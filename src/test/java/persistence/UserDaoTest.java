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


package com.kyloth.serleenacloud.persistence;

import static org.junit.Assert.*;
import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kyloth.serleenacloud.datamodel.auth.User;

public class UserDaoTest {
    @Test
    public void testConstructor() {

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        IDataSource ds = (IDataSource) context.getBean("dataSource");

        IUserDao ud = ds.userDao();

        ud.persist(new User("foo@bar.com", "psw", "Kyloth-0123"));

        User u = ud.find("foo@bar.com");
        assertTrue(u.getPassword().equals("psw"));

    }
}
