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
 * Name: SyncInputData.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.sync;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Date;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.TelemetryEvent;

/**
 * Contiene test per la classe SyncInputData.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class SyncInputDataTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        String experienceName = "Name";
        Iterable<UserPoint> userPoints = Arrays.asList(new UserPoint[] {
                                             new UserPoint(0, 0, "UserPoint_1"),
                                             new UserPoint(0, 0, "UserPoint_2")
                                         });
        Iterator<UserPoint> i_userPoints = userPoints.iterator();
        Iterable<Telemetry> telemetryData = Arrays.asList(new Telemetry[] {
                                                 new Telemetry(Arrays.asList(new TelemetryEvent[] {
                                                             new TelemetryEvent(null, new Date(100), 0),
                                                             new TelemetryEvent(null, new Date(200), 1)
                                                         }), "track"),
                                                 new Telemetry(Arrays.asList(new TelemetryEvent[] {
                                                             new TelemetryEvent(null, new Date(50), 0),
                                                             new TelemetryEvent(null, new Date(300), 1)
                                                         }), "track")
                                             });
        Iterator<Telemetry> i_telemetryData = telemetryData.iterator();
        SyncInputData sid = new SyncInputData(experienceName, userPoints, telemetryData);
        Iterable<UserPoint> sid_userPoints = sid.getUserPoints();
        Iterator<UserPoint> i_sid_userPoints = sid_userPoints.iterator();
        Iterable<Telemetry> sid_telemetryData = sid.getTelemetryData();
        Iterator<Telemetry> i_sid_telemetryData = sid_telemetryData.iterator();

        assertTrue(sid.getExperienceName().equals(experienceName));
        while(i_userPoints.hasNext() && i_sid_userPoints.hasNext()) {
            UserPoint input_userPoint = i_userPoints.next();
            UserPoint sid_userPoint = i_sid_userPoints.next();

            assertTrue(input_userPoint.getName().equals(sid_userPoint.getName()));
        }
        while(i_telemetryData.hasNext() && i_sid_telemetryData.hasNext()) {
            Telemetry input_telemetry = i_telemetryData.next();
            Telemetry sid_telemetry = i_sid_telemetryData.next();

            assertTrue(input_telemetry.compareTo(sid_telemetry) == 0);
        }
    }
}
