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
 * Name: LakeTest.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Gabriele Pozzan
 * Date: 2015-05-08
 *
 * History:
 * Version  Programmer       Date        Changes
 * 1.0.0    Gabriele Pozzan  2015-05-08  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.business;

import static org.junit.Assert.*;
import org.junit.Test;
import com.kyloth.serleenacloud.datamodel.geometry.IPoly;
import com.kyloth.serleenacloud.datamodel.geometry.IPoint;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Contiene i test di unità per la classe Lake.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class LakeTest {
    /**
     * Testa la correttezza dei costruttori e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        String name = "Loch Ness";
        double[] latitudes = {3.2, 15.6, -18.9, 47.01};
        double[] longitudes = {6.23, 99.33, -55.03, 14};
        IPoint[] points = {new Point(latitudes[0], longitudes[0]),
                   new Point(latitudes[1], longitudes[1]),
                   new Point(latitudes[2], longitudes[2]),
                   new Point(latitudes[3], longitudes[3])
        };
        Iterable<IPoint> i_points = Arrays.asList(points);
        Lake l_1 = new Lake(i_points, name);
        Lake l_2 = new Lake(points, name);
        Iterable<IPoint> l_1_points = l_1.getPoints();
        Iterable<IPoint> l_2_points = l_2.getPoints();
        Iterator<IPoint> l_1_iterator = l_1_points.iterator();
        Iterator<IPoint> l_2_iterator = l_2_points.iterator();
        Iterator<IPoint> input_iterator = i_points.iterator();

        assertTrue(l_1.getName().equals("Loch Ness"));
        while(l_1_iterator.hasNext() && l_2_iterator.hasNext()
                && input_iterator.hasNext()) {
            IPoint l_1_point = l_1_iterator.next();
            IPoint l_2_point = l_2_iterator.next();
            IPoint input_point = input_iterator.next();
            assertTrue(l_1_point.getLatitude() == input_point.getLatitude());
            assertTrue(l_2_point.getLatitude() == input_point.getLatitude());
            assertTrue(l_1_point.getLongitude() == input_point.getLongitude());
            assertTrue(l_2_point.getLongitude() == input_point.getLongitude());
        }
    }
}
