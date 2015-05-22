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
 * Name: PathDaoTest.java
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
import com.kyloth.serleenacloud.persistence.IPathDao;

import com.kyloth.serleenacloud.datamodel.business.Path;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.IWeighedPoint;
import com.kyloth.serleenacloud.datamodel.geometry.WeighedPoint;
import com.kyloth.serleenacloud.datamodel.geometry.Point;


/**
 * Contiene test per la classe PathDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class PathDaoTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static IPathDao pd;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insertPaths = "INSERT INTO Paths (Name) VALUES ('Path1'), ('Path2');";
        String insertPathPoints1 = "INSERT INTO PathPoints (PathName, Latitude, Longitude, Radius, Idx) VALUES ('Path1', 3, 7, 2, 0), ('Path1', 4, 8, 2, 1);";
        String insertPathPoints2 = "INSERT INTO PathPoints (PathName, Latitude, Longitude, Radius, Idx) VALUES ('Path2', 12, 4, 2, 0), ('Path2', 15, 7, 2, 1);";
        tpl.update(insertPaths);
        tpl.update(insertPathPoints1);
        tpl.update(insertPathPoints2);
        pd = ds.pathDao();
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
        IRect region = new Rect(new Point(10, 1), new Point(1, 10));
        Iterable<Path> paths = pd.findAll(region);
        Iterator<Path> i_paths = paths.iterator();
        Path path = i_paths.next();
        assertFalse(i_paths.hasNext());
        assertTrue(path.getName().equals("Path1"));
        Iterable<IWeighedPoint> points = path.getPoints();
        Iterator<IWeighedPoint> i_points = points.iterator();
        WeighedPoint wp1 = (WeighedPoint) i_points.next();
        WeighedPoint wp2 = (WeighedPoint) i_points.next();
        assertTrue(wp1.getLatitude() == 3);
        assertTrue(wp1.getLongitude() == 7);
        assertTrue(wp2.getLatitude() == 4);
        assertTrue(wp2.getLongitude() == 8);
        assertFalse(i_points.hasNext());
    }
}
