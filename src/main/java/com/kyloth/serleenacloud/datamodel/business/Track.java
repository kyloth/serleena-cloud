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

public class Track implements ITrack {

    private String name;
    private Iterable<CheckPoint> checkpoints;
    private Iterable<ITelemetry> telemetries;

    public Track(String name, Iterable<CheckPoint> checkpoints, Iterable<ITelemetry> telemetries) {
        this.name = name;
        this.checkpoints = checkpoints;
        this.telemetries = telemetries;
    }

    public Track(String name, CheckPoint[] checkpoints, ITelemetry[] telemetries) {
        this.name = name;
        this.checkpoints = Arrays.asList(checkpoints);
        this.telemetries = Arrays.asList(telemetries);
    }

    public Iterable<CheckPoint> getCheckPoints() {
        return checkpoints;
    }

    public String getName() {
        return name;
    }

    public Iterable<ITelemetry> getTelemetries() {
        return telemetries;
    }

    public ITelemetry getBestTelemetry() {
        ITelemetry best = null;
        for (ITelemetry telemetry : getTelemetries())
            if (best == null || best.compareTo(telemetry) > 0)
                best = telemetry;
        return best;
    }
}
