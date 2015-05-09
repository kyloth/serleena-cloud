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


package com.kyloth.serleenacloud.datamodel.sync;

import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.ITelemetry;

import java.util.Arrays;

public class SyncInputData {
    private Iterable<UserPoint> userPoints;
    private Iterable<ITelemetry> telemetryData;
    private String experienceName;

    public SyncInputData(String experienceName, Iterable<UserPoint> userPoints, Iterable<ITelemetry> telemetryData) {
        this.experienceName = experienceName;
        this.telemetryData = telemetryData;
        this.userPoints = userPoints;
    }

    public SyncInputData(String experienceName, UserPoint[] userPoints, ITelemetry[] telemetryData) {
        this.experienceName = experienceName;
        this.telemetryData = Arrays.asList(telemetryData);
        this.userPoints = Arrays.asList(userPoints);
    }

    public String getExperienceName() {
        return experienceName;
    }

    private Iterable<UserPoint> getUserPoints() {
        return userPoints;
    }

    private Iterable<ITelemetry> getTelemetryData() {
        return telemetryData;
    }

}
