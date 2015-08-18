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
 * Name: ElevationRectDaoIntegrationTest.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Nicola Mometto  Creazione file e scrittura
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
import com.kyloth.serleenacloud.persistence.IElevationRectDao;
import com.kyloth.serleenacloud.datamodel.geometry.ElevationRect;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

/**
 * Contiene test per la classe ElevationRectDao.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0.0
 */

public class ElevationRectDaoIntegrationTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static IElevationRectDao ecd;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insert_1="INSERT INTO ElevationRect (Height, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES (2, 3.5, 15.77, 12.54, 4.18);";
        String insert_2="INSERT INTO ElevationRect (Height, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES (4, 2.2, 16.18, 3.5, 14);";
        String insert_3="INSERT INTO ElevationRect (Height, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES (1, 3.2, 10.01, 15.4, 12.3);";
        tpl.update(insert_1);
        tpl.update(insert_2);
        tpl.update(insert_3);
        ecd = ds.elevationRectDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca gli ElevationRects
     * la cui area di pertinenza intersechi quella fornita come parametro.
     */

    @Test
    public void testFindAll() {
        Rect region = new Rect(new Point(16.18, 3.2), new Point(13.12, 4.9));
        Iterable<ElevationRect> el = ecd.findAll(region);
        Iterator<ElevationRect> i_el = el.iterator();
        ElevationRect ec_1 = i_el.next();
        assertTrue(ec_1.getHeight() == 2);
        ElevationRect ec_2 = i_el.next();
        assertTrue(ec_2.getHeight() == 4);
        assertFalse(i_el.hasNext());
    }


}
