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
 * Name: PointOfInterestTest.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.business;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Contiene test per la classe PointOfInterest.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class PointOfInterestTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        double latitude_1 = -12.32;
        double longitude_1 = 13.93;
        String name_1 = "Troll Camp";
        PointOfInterest.POIType type_1 = PointOfInterest.POIType.WARNING;
        double latitude_2 = 9.15;
        double longitude_2 = 77.66;
        String name_2 = "Lembas";
        PointOfInterest.POIType type_2 = PointOfInterest.POIType.FOOD;
        double latitude_3 = -1.44;
        double longitude_3 = 56.34;
        String name_3 = "The Prancing Pony";
        PointOfInterest.POIType type_3 = PointOfInterest.POIType.INFO;
        PointOfInterest p_1 = new PointOfInterest(latitude_1, longitude_1, name_1, type_1);
        PointOfInterest p_2 = new PointOfInterest(latitude_2, longitude_2, name_2, type_2);
        PointOfInterest p_3 = new PointOfInterest(latitude_3, longitude_3, name_3, type_3);

        assertTrue(p_1.getName().equals("Troll Camp"));
        assertTrue(p_2.getName().equals("Lembas"));
        assertTrue(p_3.getName().equals("The Prancing Pony"));
        assertTrue(p_1.getPOIType() == PointOfInterest.POIType.WARNING);
        assertTrue(p_2.getPOIType() == PointOfInterest.POIType.FOOD);
        assertTrue(p_3.getPOIType() == PointOfInterest.POIType.INFO);
    }
}
