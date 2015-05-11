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

public class TelemetryEvent {
    public static enum EventType {
        CHECKPOINT, HEART
    }

    private EventType type;
    private double value;
    private Date time;

    public TelemetryEvent(EventType type, Date time, double value) {
        this.type = type;
        this.time = time;
        this.value = value;
    }

    public EventType eventType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public Date getTime() {
        return time;
    }

}
