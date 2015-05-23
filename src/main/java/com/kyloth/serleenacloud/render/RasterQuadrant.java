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


package com.kyloth.serleenacloud.render;

import com.kyloth.serleenacloud.datamodel.geometry.Rect;

import java.awt.Image;

public class RasterQuadrant {

    Rect rect;

    RasterQuadrant(Renderer r, int x, int y) {

    }

    public Rect getBoundingRect() {
        return rect;
    }

    public RasterQuadrant getNorth() {
        return null;
    }

    public RasterQuadrant getSouth() {
        return null;
    }

    public RasterQuadrant getEast() {
        return null;
    }

    public RasterQuadrant getWest() {
        return null;
    }

    public Image getImage() {
        return null;
    }

}
