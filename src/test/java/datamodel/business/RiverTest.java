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
import com.kyloth.serleenacloud.datamodel.geometry.IWeighedPoint;
import com.kyloth.serleenacloud.datamodel.geometry.WeighedPoint;
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
    private IWeighedPoint[] weighed_points;
    private Iterable<IWeighedPoint> i_weighed_points;
    private Iterator<IWeighedPoint> input_iterator;
    /**
     * Imposta i dati per i test.
     */
    @Before
    public void initialize() {
        name = new String("Brandywine River");
        latitudes = new double[] {-23.14, 912.574, 23.21, 55.11};
        longitudes = new double[] {63.53, -12, -95.93, -3.16};
        radiuses = new double[] {32, 33, 17, 61};
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
        River r_1 = new River(i_weighed_points, name);
        River r_2 = new River(weighed_points, name);

        assertTrue(r_1.getName().equals("Brandywine River"));
        assertTrue(r_2.getName().equals("Brandywine River"));
    }
    /**
     * Testa la correttezza dei metodi "getter" ereditati dalla
     * superclasse astratta.
     */
    @Test
    public void testAbstractGetter() {
        River r_1 = new River(i_weighed_points, name);
        River r_2 = new River(weighed_points, name);
        Iterable<IWeighedPoint> r_1_points = r_1.getPoints();
        Iterable<IWeighedPoint> r_2_points = r_2.getPoints();
        Iterator<IWeighedPoint> r_1_iterator = r_1_points.iterator();
        Iterator<IWeighedPoint> r_2_iterator = r_2_points.iterator();

        while(r_1_iterator.hasNext() && r_2_iterator.hasNext()
                && input_iterator.hasNext()) {
            IWeighedPoint r_1_point = r_1_iterator.next();
            IWeighedPoint r_2_point = r_2_iterator.next();
            IWeighedPoint input_point = input_iterator.next();
            assertTrue(r_1_point.getLatitude() == input_point.getLatitude());
            assertTrue(r_2_point.getLatitude() == input_point.getLatitude());
            assertTrue(r_1_point.getLongitude() == input_point.getLongitude());
            assertTrue(r_2_point.getLongitude() == input_point.getLongitude());
            assertTrue(r_1_point.getRadius() == input_point.getRadius());
            assertTrue(r_2_point.getRadius() == input_point.getRadius());
        }

    }
}
