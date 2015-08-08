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
 * Name: UserTest.java
 * Package: com.kyloth.serleenacloud.datamodel.auth
 * Author: Gabriele Pozzan
 * Date: 2015-05-11
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.auth;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Contiene test per la classe User.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class UserTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        String deviceId = "Kyloth-01232";
        String email = "foo@bar.com";
        String password = "foobar1234";
        User u = new User(email, password, deviceId);
        String token = "foo@bar.com::9c4313e5d70533ec76304c65302d9694c21893bce62db80d38db3453f71df907";

        assertTrue(u.getEmail().equals("foo@bar.com"));
        assertTrue(u.getPassword().equals("foobar1234"));
        assertTrue(u.getDeviceId().equals("Kyloth-01232"));
        assertTrue(u.getAuthToken().getToken().equals(token));
    }
}
