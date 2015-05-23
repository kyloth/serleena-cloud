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
import java.util.Date;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Contiene test per la classe Telemetry.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class TelemetryTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        Date first_date = new Date(1124242);
        Date second_date = new Date(95843);
        TelemetryEvent first_event = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, first_date, 12.49);
        TelemetryEvent second_event = new TelemetryEvent(TelemetryEvent.EventType.HEART, second_date, 94.23);
        TelemetryEvent[] events = {first_event, second_event};
        Iterable<TelemetryEvent> i_events = Arrays.asList(events);
        Telemetry t1 = new Telemetry(events, "track");
        Telemetry t2 = new Telemetry(i_events, "track");
        Iterable<TelemetryEvent> t1_events = t1.getEvents();
        Iterable<TelemetryEvent> t2_events = t2.getEvents();
        Iterator<TelemetryEvent> t1_iterator = t1_events.iterator();
        Iterator<TelemetryEvent> t2_iterator = t2_events.iterator();
        Iterator<TelemetryEvent> input_iterator = i_events.iterator();
        while(t1_iterator.hasNext() && t2_iterator.hasNext()
                && input_iterator.hasNext()) {
            TelemetryEvent te1 = t1_iterator.next();
            TelemetryEvent te2 = t2_iterator.next();
            TelemetryEvent ie = input_iterator.next();

            assertTrue(te1.getValue() == ie.getValue());
            assertTrue(te2.getValue() == ie.getValue());
            assertTrue(te1.getTime().equals(ie.getTime()));
            assertTrue(te2.getTime().equals(ie.getTime()));
            assertTrue(te1.eventType() == ie.eventType());
            assertTrue(te2.eventType() == ie.eventType());
        }
    }

    /**
     * Testa la correttezza del metodo "compareTo".
     */
    @Test
    public void testCompareTo() {
        Date d1_1 = new Date(100);
        Date d1_2 = new Date(200);
        Date d1_3 = new Date(300);
        TelemetryEvent te1_1 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d1_1, 10);
        TelemetryEvent te1_2 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d1_2, 8);
        TelemetryEvent te1_3 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d1_3, 15);
        TelemetryEvent[] t1_events = {te1_1, te1_2, te1_3};
        Telemetry t1 = new Telemetry(t1_events, "track");

        Date d2_1 = new Date(100);
        Date d2_2 = new Date(200);
        Date d2_3 = new Date(300);
        TelemetryEvent te2_1 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d2_1, 10);
        TelemetryEvent te2_2 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d2_2, 8);
        TelemetryEvent te2_3 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d2_3, 15);
        TelemetryEvent[] t2_events = {te2_1, te2_2, te2_3};
        Telemetry t2 = new Telemetry(t2_events, "track");

        Date d3_1 = new Date(100);
        Date d3_2 = new Date(150);
        Date d3_3 = new Date(200);
        TelemetryEvent te3_1 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d3_1, 10);
        TelemetryEvent te3_2 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d3_2, 8);
        TelemetryEvent te3_3 = new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, d3_3, 15);
        TelemetryEvent[] t3_events = {te3_1, te3_2, te3_3};
        Telemetry t3 = new Telemetry(t3_events, "track");

        assertTrue(t1.compareTo(t2) == 0);
        assertTrue(t2.compareTo(t1) == 0);
        assertTrue(t1.compareTo(t3) == 1);
        assertTrue(t3.compareTo(t1) == -1);
    }


}
