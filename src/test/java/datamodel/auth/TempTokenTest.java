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
 * Name: TempTokenTest.java
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contiene i test di unità per la classe TempToken.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class TempTokenTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        String token = "id::c0259f22a149359bcb4b0d9de2b33e82464246b242892383dc12635ef153f72a";
        String deviceId = "id";
        TempToken tt = new TempToken(deviceId, new Date(121542));

        assertTrue(tt.getDeviceId().equals(deviceId));
        assertTrue(tt.getToken().equals(token));

    }
}
