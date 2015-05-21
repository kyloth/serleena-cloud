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
 * Name: UserDaoTest.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.persistence;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kyloth.serleenacloud.datamodel.auth.User;

/**
 * Contiene test per la classe UserDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class UserDaoTest {
    private static ApplicationContext context;
    private static IDataSource ds;
    private static IUserDao ud;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (IDataSource) context.getBean("dataSource");
        ud = ds.userDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo persist inserisca un nuovo utente nel db qualora
     * la mail dell'utente usato come parametro non fosse già presente.
     */

    @Test
    public void persistShouldInsertNewUser() {
        User new_user = new User("foo1@bar.com", "psw", "Kyloth-0");
        ud.persist(new_user);
        User u = ud.find("foo1@bar.com");
        assertTrue(u.getPassword().equals("psw"));
        assertTrue(u.getDeviceId().equals("Kyloth-0"));
    }

    /**
     * Verifica che il metodo persist aggiorni la mail di un utente già
     * presente nel db.
     */

    @Test
    public void persistShouldUpdateExistingUser() {
        User user = new User("foo2@bar.com", "psw1", "Kyloth-1");
        ud.persist(user);
        assertTrue(ud.find("foo2@bar.com").getPassword().equals("psw1"));
        User new_user = new User("foo2@bar.com", "psw2", null);
        ud.persist(new_user);
        assertTrue(ud.find("foo2@bar.com").getPassword().equals("psw2"));
    }

    /**
     * Verifica che il metodo persist aggiorni il deviceId di un utente
     * già presente nel db qualora un nuovo deviceId fosse fornito come
     * parametro all'invocazione.
     */

    @Test
    public void persistShouldUpdateDeviceIdWhenPossible() {
        User user = new User("foo3@bar.com", "psw", "Kyloth-2");
        ud.persist(user);
        assertTrue(ud.find("foo3@bar.com").getDeviceId().equals("Kyloth-2"));
        User new_user = new User("foo3@bar.com", "psw", "Kyloth-3");
        ud.persist(new_user);
        assertTrue(ud.find("foo3@bar.com").getDeviceId().equals("Kyloth-3"));
    }
}
