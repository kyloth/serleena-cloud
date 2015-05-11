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


package com.kyloth.serleenacloud.datamodel.business;

import java.util.Arrays;
import java.util.Date;

public class Telemetry implements ITelemetry {

    Iterable<TelemetryEvent> events;

    public Telemetry(Iterable<TelemetryEvent> events) {
        this.events = events;
    }

    public Telemetry(TelemetryEvent[] events) {
        this.events = Arrays.asList(events);
    }

    public Iterable<TelemetryEvent> getEvents() {
        return events;
    }

    public int compareTo(ITelemetry telemetry) {
        long thisDuration = duration(this);
        long othDuration = duration(telemetry);
        if (thisDuration == othDuration)
            return 0;
        else if (thisDuration > othDuration)
            return 1;
        else
            return -1;
    }

    private static long duration (ITelemetry telemetry) {
        long duration = 0;
        Date lastDate = null;

        for (TelemetryEvent event : telemetry.getEvents())
            if (event.eventType() == TelemetryEvent.EventType.CHECKPOINT) {
                if (lastDate != null)
                    duration += event.getTime().getTime() - lastDate.getTime();
                lastDate = event.getTime();
            }
        return duration;

    }
}
