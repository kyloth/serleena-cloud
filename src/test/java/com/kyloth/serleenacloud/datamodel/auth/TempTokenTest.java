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
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.auth;

import static org.junit.Assert.*;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Contiene test per la classe TempToken.
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
        String deviceId = "id";
        TempToken tt = new TempToken(deviceId);
        assertTrue(tt.getDeviceId().equals(deviceId));
    }
}
