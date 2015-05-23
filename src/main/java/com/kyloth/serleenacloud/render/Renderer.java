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

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.River;
import com.kyloth.serleenacloud.datamodel.business.Path;
import com.kyloth.serleenacloud.datamodel.business.Lake;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;

import java.util.ArrayList;

public class Renderer {

    static IDataSource ds = DataSourceFactory.getDataSource();

    Rect rect;

    Iterable<Lake> lakes;
    Iterable<River> rivers;
    Iterable<Path> paths;
    Iterable<PointOfInterest> pois;
    Iterable<UserPoint> ups;
    Iterable<Track> tracks;

    Renderer(Experience e) {
        this.rect = e.getBoundingRect();
        this.lakes = ds.lakeDao().findAll(rect);
        this.rivers = ds.riverDao().findAll(rect);
        this.paths = ds.pathDao().findAll(rect);
        this.pois = ds.pointOfInterestDao().findAll(rect);
        this.ups = e.getUserPoints();
        this.tracks = e.getTracks();
    }

    public Iterable<RasterQuadrant> getRasterQuadrants() {
        RasterQuadrant rq = new RasterQuadrant(this, 0, 0);
        ArrayList<RasterQuadrant> quadrants = new ArrayList<RasterQuadrant>();
        while(rq != null) {
            RasterQuadrant curr = rq;
            while(curr != null) {
                quadrants.add(curr);
                curr = curr.getEast();
            }
            rq = rq.getSouth();
        }
        return quadrants;
    }

    public static Renderer fromExperience(Experience e) {
        return new Renderer(e);
    }
}
