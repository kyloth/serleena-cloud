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
 * Name: SyncListDaoIntegrationTest.java
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

import java.util.Arrays;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.persistence.ISyncListDao;
import com.kyloth.serleenacloud.persistence.IDataSource;


/**
 * Contiene test per la classe SyncListDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class SyncListDaoIntegrationTest {
    private static ApplicationContext context;
    private static IDataSource ds;
    private static JDBCDataSource ds_user;
    private static User user;
    private static ISyncListDao sld;
    private static JdbcTemplate tpl;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */
    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (IDataSource) context.getBean("dataSource");
        user = new User("foo@bar.com", "psw", "Kyloth-1");
        ds_user = (JDBCDataSource) ds.forUser(user);
        tpl = ds_user.getTpl();
        String insertUser = "INSERT INTO Users (Email, Password, DeviceId) VALUES ('foo@bar.com', 'psw' 'Kyloth-1')";
        String insertExperiences = "INSERT INTO Experiences (Id, Name, User, NWLongitude, NWLatitude, SELongitude, SWLatitude) VALUES ('id1', 'Experience_1', 'foo@bar.com', 1, 10, 10, 1), ('id2', 'Experience_2', 'foo@bar.com', 1, 10, 10, 1)";
        tpl.update(insertUser);
        tpl.update(insertExperiences);
        sld = ds_user.syncListDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */
    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo persist inserisca le esperienze
     * in lista di sincronizzazione e che il metodo findAll le
     * recuperi.
     */
    public void testPersistAndFindAll() {
        Experience exp_1 = new Experience("Experience_1", "id1",
                                          new Rect(new Point(10, 1),
                                                  new Point(1, 10)),
                                          Arrays.asList(new Track[] {}),
                                          Arrays.asList(new UserPoint[] {}),
                                          Arrays.asList(new PointOfInterest[] {}));
        Experience exp_2 = new Experience("Experience_2", "id2",
                                          new Rect(new Point(10, 1),
                                                  new Point(1, 10)),
                                          Arrays.asList(new Track[] {}),
                                          Arrays.asList(new UserPoint[] {}),
                                          Arrays.asList(new PointOfInterest[] {}));
        sld.persist(Arrays.asList(new Experience[] {exp_1, exp_2}));
        Iterable<Experience> exp_list = sld.findAll();
        Iterator<Experience> i_exp_list = exp_list.iterator();
        assertTrue(i_exp_list.next().getName().equals("Experience_1"));
        assertTrue(i_exp_list.next().getName().equals("Experience_2"));
    }
}
