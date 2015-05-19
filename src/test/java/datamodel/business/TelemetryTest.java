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
import org.junit.Before;
import static org.mockito.Mockito.*;
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
    TelemetryEvent event_1;
    TelemetryEvent event_2;
    TelemetryEvent event_3a;
    TelemetryEvent event_3b;
    TelemetryEvent event_4;
    TelemetryEvent[] events_1;
    TelemetryEvent[] events_2;
    
    @Before
    public void initialize() {
	event_1 = mock(TelemetryEvent.class); event_2 = mock(TelemetryEvent.class);
	event_3a = mock(TelemetryEvent.class); event_3b = mock(TelemetryEvent.class);
	event_4 = mock(TelemetryEvent.class);
	when(event_1.eventType()).thenReturn(TelemetryEvent.EventType.CHECKPOINT);
	when(event_2.eventType()).thenReturn(TelemetryEvent.EventType.CHECKPOINT);
	when(event_3a.eventType()).thenReturn(TelemetryEvent.EventType.HEART);
	when(event_3b.eventType()).thenReturn(TelemetryEvent.EventType.CHECKPOINT);
	when(event_4.eventType()).thenReturn(TelemetryEvent.EventType.CHECKPOINT);
	when(event_1.getTime()).thenReturn(new Date(100));
	when(event_2.getTime()).thenReturn(new Date(150));
	when(event_3a.getTime()).thenReturn(new Date(200));
	when(event_3b.getTime()).thenReturn(new Date(200));
	when(event_4.getTime()).thenReturn(new Date(300));
	events_1 = new TelemetryEvent[] {event_1, event_2, event_3a, event_4};
	events_2 = new TelemetryEvent[] {event_1, event_2, event_3b, event_4};
    }
   
    /**
     * Verifica che il metodo compareTo resistuisca i corretti valori in
     * base ai diversi parametri.
     */
       
    @Test
    public void compareToReturnTest() {
	ITelemetry telemetry_1 = new Telemetry(Arrays.asList(events_1));
	ITelemetry telemetry_2 = new Telemetry(Arrays.asList(events_2));
	assertTrue(telemetry_1.compareTo(telemetry_2) == -1);
	assertTrue(telemetry_2.compareTo(telemetry_1) == 1);
	assertTrue(telemetry_1.compareTo(telemetry_1) == 0);
    }

}
