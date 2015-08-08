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
 * Name: EmergencyContactTest.java
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
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Contiene test per la classe EmergencyContact.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class EmergencyContactTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        Point nw = new Point(12.53, 4.11);
        Point se = new Point(9.09, 13.84);
        Point ne = new Point(12.53, 13.84);
        Point sw = new Point(9.09, 4.11);
        Rect boundingRect = new Rect(nw, se);
        String name = "Karma Police";
        String number = "01239473";
        EmergencyContact ec = new EmergencyContact(name, boundingRect, number);
        Iterable<Point> points = ec.getBoundingRect().getPoints();
        Iterable<Point> testPoints = Arrays.asList(new Point[] {nw, ne, se, sw});
        Iterator<Point> i = points.iterator();
        Iterator<Point> j = testPoints.iterator();

        assertTrue(ec.getName().equals("Karma Police"));
        assertTrue(ec.getNumber().equals("01239473"));
        while(i.hasNext() && j.hasNext()) {
            Point p = i.next();
            Point tp = j.next();
            assertTrue(p.getLatitude() == tp.getLatitude());
            assertTrue(p.getLongitude() == tp.getLongitude());
        }
    }
}
