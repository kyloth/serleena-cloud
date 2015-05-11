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
 * Name: PathTest.java
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
import org.junit.Before;
import com.kyloth.serleenacloud.datamodel.geometry.IWeighedPoint;
import com.kyloth.serleenacloud.datamodel.geometry.WeighedPoint;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Contiene i test di unità per la classe Path.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class PathTest {
    private String name;
    private double[] latitudes;
    private double[] longitudes;
    private double[] radiuses;
    private IWeighedPoint[] weighed_points;
    private Iterable<IWeighedPoint> i_weighed_points;
    private Iterator<IWeighedPoint> input_iterator;
    /**
     * Imposta i dati per i test.
     */
    @Before
    public void initialize() {
        name = new String("Path of the Dead");
        latitudes = new double[] {12.07, -44.38, 8.91, 22.76};
        longitudes = new double[] {45.92, 2.49, 88.48, 7.42};
        radiuses = new double[] {11, 23, 93, 54};
        weighed_points = new IWeighedPoint[] {new WeighedPoint(latitudes[0], longitudes[0], radiuses[0]),
                           new WeighedPoint(latitudes[1], longitudes[1], radiuses[1]),
                           new WeighedPoint(latitudes[2], longitudes[2], radiuses[2]),
                           new WeighedPoint(latitudes[3], longitudes[3], radiuses[3])
        };
        i_weighed_points = Arrays.asList(weighed_points);
        input_iterator = i_weighed_points.iterator();
    }
    /**
     * Testa la correttezza dei costruttori e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        Path p_1 = new Path(i_weighed_points, name);
        Path p_2 = new Path(weighed_points, name);

        assertTrue(p_1.getName().equals("Path of the Dead"));
        assertTrue(p_2.getName().equals("Path of the Dead"));
    }
    /**
     * Testa la correttezza dei metodi "getter" ereditati dalla
     * superclasse astratta.
     */
    @Test
    public void testAbstractGetter() {
        Path p_1 = new Path(i_weighed_points, name);
        Path p_2 = new Path(weighed_points, name);
        Iterable<IWeighedPoint> p_1_points = p_1.getPoints();
        Iterable<IWeighedPoint> p_2_points = p_2.getPoints();
        Iterator<IWeighedPoint> p_1_iterator = p_1_points.iterator();
        Iterator<IWeighedPoint> p_2_iterator = p_2_points.iterator();

        while(p_1_iterator.hasNext() && p_2_iterator.hasNext()
                && input_iterator.hasNext()) {
            IWeighedPoint p_1_point = p_1_iterator.next();
            IWeighedPoint p_2_point = p_2_iterator.next();
            IWeighedPoint input_point = input_iterator.next();
            assertTrue(p_1_point.getLatitude() == input_point.getLatitude());
            assertTrue(p_2_point.getLatitude() == input_point.getLatitude());
            assertTrue(p_1_point.getLongitude() == input_point.getLongitude());
            assertTrue(p_2_point.getLongitude() == input_point.getLongitude());
            assertTrue(p_1_point.getRadius() == input_point.getRadius());
            assertTrue(p_2_point.getRadius() == input_point.getRadius());
        }

    }
}
