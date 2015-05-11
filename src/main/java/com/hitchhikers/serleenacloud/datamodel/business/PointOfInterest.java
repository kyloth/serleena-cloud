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

import com.kyloth.serleenacloud.datamodel.geometry.Point;

public class PointOfInterest extends Point {

    public static enum POIType {
        FOOD, INFO, WARNING
    }

    String name;
    POIType type;

    PointOfInterest(double latitude, double longitude, String name, POIType type) {
        super(latitude, longitude);
        this.name = name;
        this.type = type;
    }

    String getName() {
        return name;
    }

    POIType getPOIType() {
        return type;
    }
}
