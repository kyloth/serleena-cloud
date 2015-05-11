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
 * Date: 2015-05-11
 *
 * History:
 * Version  Programmer       Date        Changes
 * 1.0.0    Gabriele Pozzan  2015-05-11  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.business;

import static org.junit.Assert.*;
import org.junit.Test;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.IPoint;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Contiene i test di unità per la classe EmergencyContact.
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
        Point nw = new Point(12.53, 24.11);
        Point se = new Point(9.09, 13.84);
        Point ne = new Point(12.53, 13.84);
        Point sw = new Point(9.09, 24.11);
        Rect boundingRect = new Rect(nw, se);
        String name = "Karma Police";
        String number = "01239473";
        EmergencyContact ec = new EmergencyContact(name, boundingRect, number);
        Iterable<IPoint> points = ec.getBoundingRect().getPoints();
        Iterable<IPoint> testPoints = Arrays.asList(new IPoint[] {nw, ne, se, sw});
        Iterator<IPoint> i = points.iterator();
        Iterator<IPoint> j = testPoints.iterator();

        assertTrue(ec.getName().equals("Karma Police"));
        assertTrue(ec.getNumber().equals("01239473"));
        while(i.hasNext() && j.hasNext()) {
            IPoint p = i.next();
            IPoint tp = j.next();
            assertTrue(p.getLatitude() == tp.getLatitude());
            assertTrue(p.getLongitude() == tp.getLongitude());
        }
    }
}
