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
 * Name: UtilTest.java
 * Package: com.kyloth.serleenacloud.datamodel.auth
 * Author: Gabriele Pozzan
 * Date: 2015-05-11
 *
 * History:
 * Version  Programmer       Date        Changes
 * 1.0.0    Gabriele Pozzan  2015-05-11  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.auth;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Contiene test per la classe Util.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class UtilTest {
    /**
     * Testa il metodo "sha256" della classe Util.
     */
    @Test
    public void testSha256() {
        String base_1 = "Would it save you a lot of time if I just gave up and went mad now?";
        String base_2 = "'This must be Thursday,' said Arthur to himself, sinking low over his beer. 'I never could get the hang of Thursdays.'";
        String base_3 = "For a moment, nothing happened. Then, after a second or so, nothing continued to happen.";
        String hash_1 = "69f9243473a68620bb3d6880fb67c9b88a465cee7c5510ca484837d64785ed29";
        String hash_2 = "9e684e26d6bd6e632de0af47739f77a374648b6e61cc18469d1ea38540c38314";
        String hash_3 = "b34df8140e434869b54b8f6fdbcc6e3c43cde5c443cc0c6e8c890e9308b8eb89";

        assertTrue(Util.sha256(base_1).equals(hash_1));
        assertTrue(Util.sha256(base_2).equals(hash_2));
        assertTrue(Util.sha256(base_3).equals(hash_3));
    }
}
