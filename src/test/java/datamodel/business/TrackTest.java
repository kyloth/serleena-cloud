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
 * Name: TrackTest.java
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Date;

/**
 * Contiene test per la classe Track.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class TrackTest {
    private String name;
    private Iterable<CheckPoint> checkpoints;
    private Iterable<Telemetry> telemetries;
    private Iterator<CheckPoint> checkpoints_iterator;
    private Iterator<Telemetry> telemetries_iterator;
    private TelemetryEvent[] first_events;
    private TelemetryEvent[] second_events;
    private Track t;
    @Before
    public void initialize() {
        String name = "Track_1";
        checkpoints = Arrays.asList(new CheckPoint[] {
                                        new CheckPoint(12.32, 15.23, 1),
                                        new CheckPoint(9.23, -12.32, 2),
                                        new CheckPoint(-4.22, 22.53, 3),
                                        new CheckPoint(-12.32, -25.22, 4)
                                    });
        first_events = new TelemetryEvent[] {
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(100), 1),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(200), 1)
        };
        second_events = new TelemetryEvent[] {
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(100), 1),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(150), 1)
        };
        telemetries = Arrays.asList(new Telemetry[] {
                                        new Telemetry(first_events),
                                        new Telemetry(second_events)
                                    });
        checkpoints_iterator = checkpoints.iterator();
        telemetries_iterator = telemetries.iterator();
        t = new Track(name, checkpoints, telemetries);
    }
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        Iterable<CheckPoint> t_checkpoints = t.getCheckPoints();
        Iterable<Telemetry> t_telemetries = t.getTelemetries();
        Iterator<CheckPoint> i_c = t_checkpoints.iterator();
        Iterator<Telemetry> i_t = t_telemetries.iterator();

        assertTrue(t.getName().equals("Track_1"));
        while (i_c.hasNext() && checkpoints_iterator.hasNext()) {
            CheckPoint input_checkpoint = checkpoints_iterator.next();
            CheckPoint t_checkpoint = i_c.next();

            assertTrue(t_checkpoint.getLatitude() == input_checkpoint.getLatitude());
            assertTrue(t_checkpoint.getLongitude() == input_checkpoint.getLongitude());
            assertTrue(t_checkpoint.getId() == input_checkpoint.getId());
        }
        while(i_t.hasNext() && telemetries_iterator.hasNext()) {
            Telemetry input_telemetry = telemetries_iterator.next();
            Telemetry t_telemetry = i_t.next();

            assertTrue(input_telemetry.compareTo(t_telemetry) == 0);
        }
    }
    /**
     * Testa la correttezza del metodo "getBestTelemetry".
     */
    @Test
    public void testGetBestTelemetry() {
        Telemetry target = new Telemetry(second_events);
        Telemetry best = t.getBestTelemetry();

        assertTrue(target.compareTo(best) == 0);
    }
}
