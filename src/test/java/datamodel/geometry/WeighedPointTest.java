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
 * Name: WeighedPointTest.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer        Changes
 * 1.0.0    Gabriele Pozzan   Creazione file e scrittura
 *                                         codice e documentazione in Javadoc.
 */

package com.kyloth.serleenacloud.datamodel.geometry;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Contiene test per la classe WeighedPoint.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class WeighedPointTest {
    /**
     * Testa la correttezza dei metodi "getter" della
     * classe.
     */
    @Test
    public void testGetters() {
        double latitude = 34;
        double longitude = 25;
        double radius = 14;
        WeighedPoint wp = new WeighedPoint(latitude, longitude, radius);

        assertTrue(wp.getRadius() == radius);
        assertTrue(wp.getRadius() != 25);
        assertTrue(wp.getRadius() != 34);
    }

}
