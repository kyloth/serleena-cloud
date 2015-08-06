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
 * Name: RiverDaoTest.java
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
    private static IRiverDao pd;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insertRivers = "INSERT INTO Rivers (Name) VALUES ('River1'), ('River2');";
        String insertRiverPoints1 = "INSERT INTO RiverPoints (RiverName, Latitude, Longitude, Idx) VALUES ('River1', 3, 7, 0), ('River1', 4, 8, 1);";
        String insertRiverPoints2 = "INSERT INTO RiverPoints (RiverName, Latitude, Longitude, Idx) VALUES ('River2', 12, 4, 0), ('River2', 15, 7, 1);";
        tpl.update(insertRivers);
        tpl.update(insertRiverPoints1);
        tpl.update(insertRiverPoints2);
        pd = ds.riverDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca i sentieri la
     * cui traiettoria intersechi quella della regione fornita come
     * parametro.
     */

    @Test
    public void testFindAll() {
        Rect region = new Rect(new Point(10, 1), new Point(1, 10));
        Iterable<River> rivers = pd.findAll(region);
        Iterator<River> i_rivers = rivers.iterator();
        River river = i_rivers.next();
        assertFalse(i_rivers.hasNext());
        assertTrue(river.getName().equals("River1"));
        Iterable<Point> points = river.getPoints();
        Iterator<Point> i_points = points.iterator();
        Point wp1 = i_points.next();
        Point wp2 = i_points.next();
        assertTrue(wp1.getLatitude() == 3);
        assertTrue(wp1.getLongitude() == 7);
        assertTrue(wp2.getLatitude() == 4);
        assertTrue(wp2.getLongitude() == 8);
        assertFalse(i_points.hasNext());
    }
}
