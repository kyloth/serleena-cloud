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

import com.kyloth.serleenacloud.datamodel.geometry.AWideLine;
import com.kyloth.serleenacloud.datamodel.geometry.IWeighedPoint;

public class River extends AWideLine {

    private String name;

    public River(Iterable<IWeighedPoint> points, String name) {
        super(points);
        this.name = name;
    }

    public River(IWeighedPoint[] points, String name) {
        super(points);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
