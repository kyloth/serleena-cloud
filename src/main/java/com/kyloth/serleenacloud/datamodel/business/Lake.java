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

import com.kyloth.serleenacloud.datamodel.geometry.IPoly;
import com.kyloth.serleenacloud.datamodel.geometry.IPoint;

import java.util.Arrays;

public class Lake implements IPoly {

    private Iterable<IPoint> points;
    private String name;

    public Lake(Iterable<IPoint> points, String name) {
        this.points = points;
        this.name = name;
    }

    public Lake(IPoint[] points, String name) {
        this.points = Arrays.asList(points);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Iterable<IPoint> getPoints() {
        return points;
    }
}
