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
 * Name: WeatherForecast.java
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
import java.util.Date;
import java.util.Arrays;
import java.util.Iterator;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;

/**
 * Contiene test per la classe WeatherForecast.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class WeatherForecastTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        WeatherForecast.WeatherCondition forecast = WeatherForecast.WeatherCondition.SNOWY;
        Point nw = new Point(12.32, 74.23);
        Point se = new Point(7.44, 44);
        Point ne = new Point(12.32, 44);
        Point sw = new Point(7.44, 74.23);
        Iterable<Point> points = Arrays.asList(new Point[] {nw, ne, se, sw});
        Iterator<Point> points_iterator = points.iterator();
        Rect boundingRect = new Rect(nw, se);
        double temperature = 88.23;
        Date time = new Date(1500);
        WeatherForecast.Forecast morning = new WeatherForecast.Forecast(temperature, forecast);
        WeatherForecast wf = new WeatherForecast(time, boundingRect, morning, null, null);
        Rect wf_rect = wf.getBoundingRect();
        Iterable<Point> wf_points = wf_rect.getPoints();
        Iterator<Point> wf_iterator = wf_points.iterator();

        assertTrue(wf.getMorning().getForecast() == WeatherForecast.WeatherCondition.SNOWY);
        assertTrue(wf.getMorning().getTemperature() == 88.23);
        assertTrue(wf.getTime().equals(time));
        while(points_iterator.hasNext() && wf_iterator.hasNext()) {
            Point input_point = points_iterator.next();
            Point wf_point = wf_iterator.next();

            assertTrue(input_point.getLatitude() == wf_point.getLatitude());
            assertTrue(input_point.getLongitude() == wf_point.getLongitude());
        }
    }
}
