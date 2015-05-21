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
 * Name: EmergencyContactDaoTest.java
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
import com.kyloth.serleenacloud.datamodel.business.IEmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

/**
 * Contiene i test di unità per la classe EmergencyContactDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class EmergencyContactDaoTest {
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
        tpl.update(insert_1);
        tpl.update(insert_2);
        tpl.update(insert_3);
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
        IRect region = new Rect(new Point(16.18, 3.2), new Point(13.12, 4.9));
        Iterable<IEmergencyContact> contacts = ecd.findAll(region);
        Iterator<IEmergencyContact> i_contacts = contacts.iterator();
        EmergencyContact ec_1 = (EmergencyContact) i_contacts.next();
        assertTrue(ec_1.getName().equals("Contact_1"));
        EmergencyContact ec_2 = (EmergencyContact) i_contacts.next();
        assertTrue(ec_2.getName().equals("Contact_2"));
        assertFalse(i_contacts.hasNext());
    }


}
