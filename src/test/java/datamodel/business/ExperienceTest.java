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
 * Name: TelemetryTest.java
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
import java.util.Arrays;
import java.util.Iterator;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;

/**
 * Contiene test per la classe Experience.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class ExperienceTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        String name = "Jimi Hendrix Experience";
        Point nw = new Point(5.32, 32.65);
        Point se = new Point(2.12, 16.59);
        Point ne = new Point(5.32, 16.59);
        Point sw = new Point(2.12, 32.65);
        Iterable<Point> points = Arrays.asList(new Point[] {nw, ne, se, sw});
        Iterator<Point> points_iterator = points.iterator();
        Rect rect = new Rect(nw, se);
        Iterable<Track> tracks = Arrays.asList(new Track[] {
                                                    new Track("Track_1", new CheckPoint[1], new Telemetry[1]),
                                                    new Track("Track_2", new CheckPoint[1], new Telemetry[1])
                                                });
        Iterator<Track> tracks_iterator = tracks.iterator();
        Iterable<UserPoint> userPoints = Arrays.asList(new UserPoint[] {
                                             new UserPoint(0, 0, "up_1"),
                                             new UserPoint(0, 0, "up_2")
                                         });
        Iterator<UserPoint> userPoints_iterator = userPoints.iterator();
        Iterable<PointOfInterest> pois = Arrays.asList(new PointOfInterest[] {
                                             new PointOfInterest(0, 0, "poi_1", PointOfInterest.POIType.FOOD),
                                             new PointOfInterest(0, 0, "poi_2", PointOfInterest.POIType.FOOD)
                                         });
        Iterator<PointOfInterest> pois_iterator = pois.iterator();
        Experience e = new Experience(name, rect, tracks, userPoints, pois);
        Rect e_rect = e.getBoundingRect();
        Iterable<Point> e_points = e_rect.getPoints();
        Iterator<Point> e_points_iterator = e_points.iterator();
        Iterable<Track> e_tracks = e.getTracks();
        Iterator<Track> e_tracks_iterator = e_tracks.iterator();
        Iterable<UserPoint> e_userPoints = e.getUserPoints();
        Iterator<UserPoint> e_userPoints_iterator = e_userPoints.iterator();
        Iterable<PointOfInterest> e_pois = e.getPOIs();
        Iterator<PointOfInterest> e_pois_iterator = e_pois.iterator();


        assertTrue(e.getName().equals("Jimi Hendrix Experience"));
        while(points_iterator.hasNext() && e_points_iterator.hasNext()) {
            Point input_point = points_iterator.next();
            Point e_point = e_points_iterator.next();

            assertTrue(input_point.getLatitude() == e_point.getLatitude());
            assertTrue(input_point.getLongitude() == e_point.getLongitude());
        }
        while(tracks_iterator.hasNext() && e_tracks_iterator.hasNext()) {
            Track input_track = tracks_iterator.next();
            Track e_track = e_tracks_iterator.next();

            assertTrue(input_track.getName().equals(e_track.getName()));
        }
        while(userPoints_iterator.hasNext() && e_userPoints_iterator.hasNext()) {
            UserPoint input_userPoint = userPoints_iterator.next();
            UserPoint e_userPoint = e_userPoints_iterator.next();

            assertTrue(input_userPoint.getName().equals(e_userPoint.getName()));
        }
        while(pois_iterator.hasNext() && e_pois_iterator.hasNext()) {
            PointOfInterest input_poi = pois_iterator.next();
            PointOfInterest e_poi = e_pois_iterator.next();

            assertTrue(input_poi.getName().equals(e_poi.getName()));
        }
    }
}
