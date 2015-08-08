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
 * Name: LakeDaoTest.java
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
import com.kyloth.serleenacloud.persistence.ILakeDao;

import com.kyloth.serleenacloud.datamodel.business.Lake;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.ILakeDao;


/**
 * Contiene test per la classe LakeDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class LakeDaoTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static ILakeDao ld;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insert_lakes = "INSERT INTO Lakes (Name) VALUES ('Lake1'), ('Lake2');";
        String insert_lakepoints_1 = "INSERT INTO LakePoints (LakeName, Latitude, Longitude, Idx) VALUES ('Lake1', 3, 7, 0), ('Lake1', 4, 12, 1), ('Lake1', 8, 12, 2);";
        String insert_lakepoints_2 = "INSERT INTO LakePoints (LakeName, Latitude, Longitude, Idx) VALUES ('Lake2', 12, 4, 0), ('Lake2', 15, 7, 1);";
        tpl.update(insert_lakes);
        tpl.update(insert_lakepoints_1);
        tpl.update(insert_lakepoints_2);
        ld = ds.lakeDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca i laghi compresi
     * nella regione fornita come paramentro e il relativo insieme
     * di punti.
     */

    @Test
    public void testFindAll() {
        Rect region = new Rect(new Point(10, 1), new Point(1, 10));
        Iterable<Lake> lakes = ld.findAll(region);
        Iterator<Lake> i_lakes = lakes.iterator();
        Lake lake = i_lakes.next();
        assertTrue(lake.getName().equals("Lake1"));
        assertFalse(i_lakes.hasNext());
        Iterable<Point> points = lake.getPoints();
        Iterator<Point> i_points = points.iterator();
        Point p1 = i_points.next();
        Point p2 = i_points.next();
        Point p3 = i_points.next();
        assertTrue(p1.getLatitude() == 3);
        assertTrue(p1.getLongitude() == 7);
        assertTrue(p2.getLatitude() == 4);
        assertTrue(p2.getLongitude() == 12);
        assertTrue(p3.getLatitude() == 8);
        assertTrue(p3.getLongitude() == 12);
        assertFalse(i_points.hasNext());
    }
}
