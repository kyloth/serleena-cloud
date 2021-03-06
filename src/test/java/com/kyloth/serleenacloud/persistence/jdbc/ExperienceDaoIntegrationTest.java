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
 * Name: ExperienceDaoIntegrationTest.java
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
import com.kyloth.serleenacloud.persistence.IExperienceDao;

import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.persistence.IExperienceDao;
import com.kyloth.serleenacloud.persistence.ITrackDao;

/**
 * Contiene test per la classe ExperienceDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class ExperienceDaoIntegrationTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JDBCDataSource ds_user;
    private static JdbcTemplate tpl;
    private static IExperienceDao ed;
    private static User user;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        user = new User("foo@bar.com", "psw", "Kyloth-1");
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        ds_user = (JDBCDataSource) ds.forUser(user);
        tpl = ds_user.getTpl();
        String userInsert = "INSERT INTO Users (Email, Password, DeviceId) VALUES ('foo@bar.com', 'psw', 'Kyloth-1');";
        String expInsert = "INSERT INTO Experiences (Id, Name, User, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES ('id1','Experience', 'foo@bar.com', 2.0, 10.0, 7.0, 2.0);";
        String expUPInsert = "INSERT INTO ExperienceUserPoints (ExperienceId, Longitude, Latitude) VALUES ('id1', 3.0, 3.0), ('id1', 5.0, 5.0);";
        String insertPOIs = "INSERT INTO POIs (Name, Longitude, Latitude, Type) VALUES ('POI1', 4.0, 4.0, 'INFO'), ('POI2', 6.0, 6.0, 'WARNING');";
        String insertExperiencePOIs = "INSERT INTO ExperiencePOIs (ExperienceId, POIName) VALUES ('id1', 'POI1'), ('id1', 'POI2');";
        tpl.update(userInsert);
        tpl.update(expInsert);
        tpl.update(expUPInsert);
        tpl.update(insertPOIs);
        tpl.update(insertExperiencePOIs);
        ed = ds_user.experienceDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca le esperienze caricate
     * nel db con i relativi Punti Utente e Punti di Interesse.
     */

    @Test
    public void testFindAll() {
        Iterable<Experience> experiences = ed.findAll();
        Iterator<Experience> i_experiences = experiences.iterator();
        Experience exp = i_experiences.next();
        assertTrue(exp.getId().equals("id1"));
        Iterable<PointOfInterest> pointsOfInterest = exp.getPOIs();
        Iterator<PointOfInterest> i_POIs = pointsOfInterest.iterator();
        PointOfInterest poi_1 = i_POIs.next();
        PointOfInterest poi_2 = i_POIs.next();
        assertTrue(poi_1.getName().equals("POI1"));
        assertTrue(poi_2.getName().equals("POI2"));
        assertFalse(i_POIs.hasNext());
    }

    /**
     * Verifica che il metodo persist aggiorni l'esperienza
     */

    @Test
    public void testUpdate() {
        Experience exp = ed.find("id1");
        ed.persist(new Experience("Experience modificata", "id1", exp.getBoundingRect(), exp.getTracks(), exp.getUserPoints(), exp.getPOIs()));
        assertEquals("Experience modificata", ed.find("id1").getName());
    }
}
