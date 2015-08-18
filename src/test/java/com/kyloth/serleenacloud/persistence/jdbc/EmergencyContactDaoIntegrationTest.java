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
 * Name: EmergencyContactDaoIntegrationTest.java
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
import com.kyloth.serleenacloud.persistence.IEmergencyContactDao;
import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

/**
 * Contiene test per la classe EmergencyContactDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class EmergencyContactDaoIntegrationTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static IEmergencyContactDao ecd;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insert_1="INSERT INTO EmergencyContacts (Name, Number, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES ('Contact_1', '01', 3.5, 15.77, 12.54, 4.18);";
        String insert_2="INSERT INTO EmergencyContacts (Name, Number, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES ('Contact_2', '02', 2.2, 16.18, 3.5, 14);";
        String insert_3="INSERT INTO EmergencyContacts (Name, Number, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES ('Contact_3', '03', 3.2, 10.01, 15.4, 12.3);";
        String insert_4="INSERT INTO EmergencyContacts (Name, Number, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES ('Contact_4', '04', 11.646284, 45.277693, 11.650876, 45.274461);";
        tpl.update(insert_1);
        tpl.update(insert_2);
        tpl.update(insert_3);
        tpl.update(insert_4);
        ecd = ds.emergencyContactDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca gli EmergencyContacts
     * la cui area di pertinenza intersechi quella fornita come parametro.
     */

    @Test
    public void testFindAll() {
        Rect region = new Rect(new Point(16.18, 3.2), new Point(13.12, 4.9));
        Iterable<EmergencyContact> contacts = ecd.findAll(region);
        Iterator<EmergencyContact> i_contacts = contacts.iterator();
        EmergencyContact ec_1 = i_contacts.next();
        assertTrue(ec_1.getName().equals("Contact_1"));
        EmergencyContact ec_2 = i_contacts.next();
        assertTrue(ec_2.getName().equals("Contact_2"));
        assertFalse(i_contacts.hasNext());
    }

    @Test
    public void testRealValues() {
        //Punto NW della regione cade all'interno della regione del contatto
        Rect region = new Rect(new Point(45.275700, 11.649052), new Point(45.273540, 11.656369));
        Iterator<EmergencyContact> i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Punto SE della regione cade all'interno della regione del contatto
        region = new Rect(new Point(45.279912, 11.643151), new Point(45.275745, 11.649224));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Punto NE della regione cade all'interno della regione del contatto
        region = new Rect(new Point(45.275866, 11.639632), new Point(45.269483, 11.649230));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Punto SW della regione cade all'interno della regione del contatto
        region = new Rect(new Point(45.281281, 11.648720), new Point(45.275427, 11.659335));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Regione completamente contenuta in quella del contatto
        region = new Rect(new Point(45.275876, 11.648643), new Point(45.275230, 11.649536));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Punto NW della regione del contatto cade all'interno della regione
        region = new Rect(new Point(45.278123, 11.645123), new Point(45.276123, 11.647123));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Punto SE della regione del contatto cade all'interno della regione
        region = new Rect(new Point(45.277512, 11.647123), new Point(45.265123, 11.660123));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Punto NE della regione del contatto cade all'interno della regione
        region = new Rect(new Point(45.275123, 11.630123), new Point(45.265123, 11.648123));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Punto SW della regione del contatto cade all'interno della regione
        region = new Rect(new Point(45.279123, 11.649876), new Point(45.276834, 11.651647));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
        //Regione del contatto completamente contenuta nella regione
        region = new Rect(new Point(45.278656, 11.639536), new Point(45.273758, 11.650991));
        i_contacts = ecd.findAll(region).iterator();
        assertTrue(i_contacts.next().getName().equals("Contact_4"));
        assertFalse(i_contacts.hasNext());
    }


}
