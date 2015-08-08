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
 * Name: ElevationRectTest.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer         Changes
 * 1.0.0    Gabriele Pozzan    Creazione file e scrittura
 *                                           codice e documentazione in Javadoc
 */

package com.kyloth.serleenacloud.datamodel.geometry;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Contiene test per la classe ElevationRect.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class ElevationRectTest {
    /**
     * Testa la correttezza del costruttore e del metodo
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        Point nw = new Point(12, 4);
        Point se = new Point(6, 15);
        int height = 100;
        ElevationRect er = new ElevationRect(nw, se, height);

        assertTrue(er.getHeight() == height);
    }
}
