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
 * Name: AuthTokenTest.java
 * Package: com.kyloth.serleenacloud.datamodel.auth
 * Author: Gabriele Pozzan
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
 * Contiene test per la classe AuthToken.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class AuthTokenTest {
    /**
     * Testa la correttezza dei costruttori e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        User first_user = new User("bar@foo.etc", "fubar9876", "Kyloth-99");
        AuthToken a1 = new AuthToken(first_user);

        assertTrue(a1.getToken().equals("bar@foo.etc::66dcb26f6677d112f253ce606a1e5ce7dd6f37d0dd71a02280327360915ad914"));
    }
    /**
     * Testa la correttezza del metodo "validFor" della classe.
     */
    @Test
    public void testValidFor() {
        User second_user = new User("fubar@baz.etc", "666-qux", "Kyloth-100");
        User third_user = new User("baz@qux.etc", "321-baz", "Kyloth-110");
        String token = "fubar@baz.etc::52a1379fcbfa5cb588bdc4bbcd9a9675dad27cdb8f2b0b7ff7adeb40a841029f";
        AuthToken au_1 = new AuthToken(token);
        AuthToken au_2 = new AuthToken(second_user);

        assertTrue(au_1.validFor(second_user));
        assertTrue(au_2.validFor(second_user));
        assertFalse(au_1.validFor(third_user));
        assertFalse(au_2.validFor(third_user));
    }
}
