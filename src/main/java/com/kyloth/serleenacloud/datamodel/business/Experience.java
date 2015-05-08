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

import com.kyloth.serleenacloud.datamodel.geometry.IRect;
//import com.kyloth.serleenacloud.render.IRasterQuadrant;
//import com.kyloth.serleenacloud.render.Renderer;

import java.util.Arrays;

public class Experience implements IExperience {

    private Iterable<ITrack> tracks;
    private Iterable<UserPoint> userPoints;
    private Iterable<PointOfInterest> pois;
    private IRect rect;
    private String name;

    public Experience(String name, IRect rect, Iterable<ITrack> tracks, Iterable<UserPoint> userPoints, Iterable<PointOfInterest> pois) {
        this.name = name;
        this.rect = rect;
        this.tracks = tracks;
        this.userPoints = userPoints;
        this.pois = pois;
    }

    public Experience(String name, IRect rect, ITrack[] tracks, UserPoint[] userPoints, PointOfInterest[] pois) {
        this.name = name;
        this.rect = rect;
        this.tracks = Arrays.asList(tracks);
        this.userPoints = Arrays.asList(userPoints);
        this.pois = Arrays.asList(pois);
    }

    public Iterable<ITrack> getTracks() {
        return tracks;
    }

    public Iterable<UserPoint> getUserPoints() {
        return userPoints;
    }

    public IRect getBoundingRect() {
        return rect;
    }

    public String getName() {
        return name;
    }

    public Iterable<PointOfInterest> getPOIs() {
        return pois;
    }

    // public Iterable<IRasterQuadrant> getRasterData() {
    //     Renderer.fromExperience(this).getRasterQuadrants();
    // }

}
