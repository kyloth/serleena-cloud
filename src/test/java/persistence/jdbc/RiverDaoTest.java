/******************************************************************************
* Copyright (c) 2015 Nicola Mometto
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*  Nicola Mometto
*  Antonio Cavestro
*  Sebastiano Valle
*  Gabriele Pozzan
******************************************************************************/


/**
 * Name: PointOfInterestDaoTest.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kyloth.serleenacloud.persistence.jdbc.JDBCDataSource;
import com.kyloth.serleenacloud.persistence.IRiverDao;

import com.kyloth.serleenacloud.datamodel.business.River;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.geometry.WeighedPoint;

/**
 * Contiene test per la classe RiverDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class RiverDaoTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static IRiverDao rd;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insertRivers = "INSERT INTO Rivers (Name) VALUES ('River1'), ('River2');";
        String insertRiverPoints_1 = "INSERT INTO RiverPoints (RiverName, Latitude, Longitude, Radius, Idx) VALUES ('River1', 3, 2, 1, 0), ('River1', 5, 4, 1, 1), ('River1', 7, 6, 1, 2);";
        String insertRiverPoints_2 = "INSERT INTO RiverPoints (RiverName, Latitude, Longitude, Radius, Idx) VALUES ('River2', 12, 11, 1, 0), ('River2', 7, 15, 1, 1);";
        tpl.update(insertRivers);
        tpl.update(insertRiverPoints_1);
        tpl.update(insertRiverPoints_2);
        rd = ds.riverDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca tutti i fiumi
     * il cui percorso intersechi la regione fornita come parametro.
     */

    @Test
    public void testFindAll() {
        Rect region = new Rect(new Point(10, 1), new Point(1, 10));
        Iterable<River> rivers = rd.findAll(region);
        Iterator<River> i_rivers = rivers.iterator();
        River river = i_rivers.next();
        assertFalse(i_rivers.hasNext());
        Iterable<WeighedPoint> points = river.getPoints();
        Iterator<WeighedPoint> i_points = points.iterator();
        WeighedPoint wp1 = i_points.next();
        WeighedPoint wp2 = i_points.next();
        WeighedPoint wp3 = i_points.next();
        assertTrue(wp1.getLatitude() == 3);
        assertTrue(wp1.getLongitude() == 2);
        assertTrue(wp2.getLatitude() == 5);
        assertTrue(wp2.getLongitude() == 4);
        assertTrue(wp3.getLatitude() == 7);
        assertTrue(wp3.getLongitude() == 6);
        assertFalse(i_points.hasNext());
    }

}
