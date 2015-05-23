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
import com.kyloth.serleenacloud.persistence.IPointOfInterestDao;

import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

/**
 * Contiene test per la classe PointOfInterestDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class PointOfInterestDaoTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static IPointOfInterestDao poid;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insertPOIs = "INSERT INTO POIs (Name, Latitude, Longitude, Type) VALUES ('POI1', 2, 4, 'FOOD'), ('POI2', 6, 8, 'INFO'), ('POI3', 12, 18, 'WARNING');";
        tpl.update(insertPOIs);
        poid = ds.pointOfInterestDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca tutti i Punti
     * di Interesse contenuti nella regione fornita come parametro.
     */

    @Test
    public void testFindAll() {
        Rect region = new Rect(new Point(10, 1), new Point(1, 10));
        Iterable<PointOfInterest> pois = poid.findAll(region);
        Iterator<PointOfInterest> i_pois = pois.iterator();
        PointOfInterest poi1 = i_pois.next();
        PointOfInterest poi2 = i_pois.next();
        assertTrue(poi1.getName().equals("POI1"));
        assertTrue(poi2.getName().equals("POI2"));
        assertFalse(i_pois.hasNext());
    }
}
