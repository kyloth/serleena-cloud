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
 * Name: RiverTest.java
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
import org.junit.Before;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Contiene test per la classe River.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class RiverTest {
    private String name;
    private double[] latitudes;
    private double[] longitudes;
    private double[] radiuses;
    private Point[] points;
    private Iterable<Point> i_points;
    private Iterator<Point> input_iterator;
    /**
     * Imposta i dati per i test.
     */
    @Before
    public void initialize() {
        name = new String("River of the Dead");
        latitudes = new double[] {12.07, -44.38, 8.91, 22.76};
        longitudes = new double[] {45.92, 2.49, 88.48, 7.42};
        points = new Point[] {new Point(latitudes[0], longitudes[0]),
                              new Point(latitudes[1], longitudes[1]),
                              new Point(latitudes[2], longitudes[2]),
                              new Point(latitudes[3], longitudes[3])
        };
        i_points = Arrays.asList(points);
        input_iterator = i_points.iterator();
    }
    /**
     * Testa la correttezza dei costruttori e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        River p_1 = new River(i_points, name);
        River p_2 = new River(points, name);

        assertTrue(p_1.getName().equals("River of the Dead"));
        assertTrue(p_2.getName().equals("River of the Dead"));
    }
    /**
     * Testa la correttezza dei metodi "getter" ereditati dalla
     * superclasse astratta.
     */
    @Test
    public void testAbstractGetter() {
        River p_1 = new River(i_points, name);
        River p_2 = new River(points, name);
        Iterable<Point> p_1_points = p_1.getPoints();
        Iterable<Point> p_2_points = p_2.getPoints();
        Iterator<Point> p_1_iterator = p_1_points.iterator();
        Iterator<Point> p_2_iterator = p_2_points.iterator();

        while(p_1_iterator.hasNext() && p_2_iterator.hasNext()
                && input_iterator.hasNext()) {
            Point p_1_point = p_1_iterator.next();
            Point p_2_point = p_2_iterator.next();
            Point input_point = input_iterator.next();
            assertTrue(p_1_point.getLatitude() == input_point.getLatitude());
            assertTrue(p_2_point.getLatitude() == input_point.getLatitude());
            assertTrue(p_1_point.getLongitude() == input_point.getLongitude());
            assertTrue(p_2_point.getLongitude() == input_point.getLongitude());
        }

    }
}
