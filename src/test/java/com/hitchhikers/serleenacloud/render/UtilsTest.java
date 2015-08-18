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
 * Name: UtilsTest.java
 * Package: com.kyloth.serleenacloud.render
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Nicola Mometto  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.render;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Iterator;

import java.awt.Color;
/**
 * Contiene test per la classe di utilità Utils.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0.0
 */

public class UtilsTest {

    /**
     * Testa che il metodo di utilità Utils.colorFromString(String)
     * ritorni un oggetto Color per input valido
     */

    @Test
    public void colorFromStringTest() {
        assertEquals(Color.CYAN, Utils.colorFromString("CYAN"));
    }

    /**
     * Testa che le variabili statiche siano correttamente inizializzate
     */

    @Test
    public void initializedConfigTest() {
        assertTrue(Utils.factor > 0);
        assertNotNull(Utils.trackLineColor);
        assertNotNull(Utils.checkPointColor);
        assertNotNull(Utils.lakeColor);
        assertNotNull(Utils.pathColor);
        assertNotNull(Utils.riverColor);
        assertNotNull(Utils.backgroundColor);
        assertNotNull(Utils.elevationColor);
        assertNotNull(Utils.up);
        assertNotNull(Utils.food);
        assertNotNull(Utils.info);
        assertNotNull(Utils.warning);
    }


    /**
     * Testa che i metodi di proiezione siano corretti
     */

    @Test
    public void projTest() {
        assertTrue(Utils.projLatitude(34.1234) == 36.34029999886815);
        assertTrue((Utils.projLatitude(60) - Utils.projLatitude(50))
                   < (Utils.projLatitude(80) - Utils.projLatitude(70)));
        assertTrue(34.1234 == Utils.projY(Utils.projLatitude(34.1234)));
    }


    /**
     * Testa che i metodi di utilità siano corretti
     */

    @Test
    public void utilTest() {
        assertTrue(3 == Utils.round(3.2));
        assertTrue(3 == Utils.round(2.8));
        assertTrue(15.0 == Utils.multipleOf(14.2, 5));
    }


}
