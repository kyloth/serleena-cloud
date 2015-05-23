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
 * Name: RectTest.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                        codice e documentazione in Javadoc
 */

package com.kyloth.serleenacloud.datamodel.geometry;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Contiene test per la classe Rect.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class RectTest {
    /**
     * Testa la correttezza del costruttore della classe
     */
    @Test
    public void testConstructor() {
        Point nw = new Point(12, 24);
        Point se = new Point(5, 10);
        Rect r = new Rect(nw, se);

        assertTrue(r.getNWPoint().getLatitude() == nw.getLatitude());
        assertTrue(r.getNWPoint().getLongitude() == nw.getLongitude());
        assertTrue(r.getSEPoint().getLatitude() == se.getLatitude());
        assertTrue(r.getSEPoint().getLongitude() == se.getLongitude());
    }
    /**
     * Testa la correttezza del metodo getPoints
     *
     * In particolare verifica che i punti ne e sw siano creati
     * correttamente.
     */

    @Test
    public void testGetPoints() {
        Point nw = new Point(15, 50);
        Point se = new Point(10, 35);
        Rect r = new Rect(nw, se);
        Point ne = new Point(15, 35);
        Point sw = new Point(10, 50);
        Iterable<Point> points = r.getPoints();
        Iterable<Point> testPoints = Arrays.asList(new Point[] {nw, ne, se, sw });
        Iterator<Point> i = points.iterator();
        Iterator<Point> j = testPoints.iterator();

        while(i.hasNext() && j.hasNext()) {
            Point p = i.next();
            Point tp = j.next();
            assertTrue(p.getLatitude() == tp.getLatitude());
            assertTrue(p.getLongitude() == tp.getLongitude());
        }
    }
}
