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


/**
 * Name: Experience.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.Rect;

import com.kyloth.serleenacloud.render.RasterQuadrant;
import com.kyloth.serleenacloud.render.Renderer;

import com.fasterxml.jackson.annotation.JsonGetter;
import java.util.Arrays;

/**
 * Classe che rappresenta un'esperienza.
 *
 * @use Viene utilizzata da DataRestController e ExperienceRestController per creare o elaborare il JSON fornito o richiesto da frontend e applicazione android. Utilizza Render.Renderer per creare in modo lazy i quadranti raster.
 * @field tracks : Iterable<Track> Insieme dei percorsi relativi all'esperienza
 * @field userPoints : Iterable<UserPoint> Insieme dei punti utente relativi all'esperienza
 * @field pois : Iterable<PointOfInterest> Insieme dei punti di interesse relativi all'esperienza
 * @field rect : Rect Oggetto rappresentante l'area geografica relativa all'esperienza
 * @field name : String Nome dell'esperienza
 * @field id : String Id unico per l'esperienza
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Experience {

    /**
     * Insieme dei percorsi relativi all'esperienza.
     */

    private Iterable<Track> tracks;

    /**
     * Insieme dei punti utente relativi all'esperienza.
     */

    private Iterable<UserPoint> userPoints;

    /**
     * Insieme dei punti di interesse relativi all'esperienza.
     */

    private Iterable<PointOfInterest> pois;

    /**
     * Rappresenta l'area geografica in cui è situata l'esperienza.
     */

    private Rect rect;

    /**
     * Nome dell'esperienza.
     */

    private String name;

    /**
     * Id dell'esperienza.
     */

    private String id;

    /**
     * Crea un oggetto Experience inizializzandone i campi dati.
     *
     * @param name Il nome dell'esperienza.
     * @param id L'id dell'esperienza.
     * @param rect L'area geografica in cui è situata l'esperienza.
     * @param tracks L'insieme dei percorsi relativi all'esperienza.
     * @param userPoints L'insieme dei punti utente relativi all'esperienza.
     * @param pois L'insieme dei punti di interesse relativi all'esperienza.
     */

    public Experience(String name, String id, Rect rect, Iterable<Track> tracks, Iterable<UserPoint> userPoints, Iterable<PointOfInterest> pois) {
        this.name = name;
        this.id = id;
        this.rect = rect;
        this.tracks = tracks;
        this.userPoints = userPoints;
        this.pois = pois;
    }

    /**
     * Crea un oggetto Experience inizializzandone i campi dati.
     *
     * @param name Il nome dell'esperienza.
     * @param id L'id dell'esperienza.
     * @param rect L'area geografica in cui è situata l'esperienza.
     * @param tracks L'array dei percorsi relativi all'esperienza.
     * @param userPoints L'array dei punti utente relativi all'esperienza.
     * @param pois L'array dei punti di interesse relativi all'esperienza.
     */

    public Experience(String name, String id, Rect rect, Track[] tracks, UserPoint[] userPoints, PointOfInterest[] pois) {
        this.name = name;
        this.id = id;
        this.rect = rect;
        this.tracks = Arrays.asList(tracks);
        this.userPoints = Arrays.asList(userPoints);
        this.pois = Arrays.asList(pois);
    }

    /**
     * Metodo getter che permette di ottenere l'insieme dei percorsi relativi all'esperienza.
     *
     * @return Restituisce l'insieme dei percorsi relativi all'esperienza.
     */

    public Iterable<Track> getTracks() {
        return tracks;
    }

    /**
     * Metodo getter che permette di ottenere l'insieme dei punti utente relativi all'esperienza.
     *
     * @return Restituisce l'insieme dei punti utente relativi all'esperienza.
     */

    @JsonGetter("user_points")
    public Iterable<UserPoint> getUserPoints() {
        return userPoints;
    }

    /**
     * Metodo getter che permette di ottenere un oggetto di tipo IRect che rappresenta l'area geografica relativa all'esperienza.
     *
     * @return Restituisce un Rect che rappresenta l'area geografica dell'esperienza.
     */

    public Rect getBoundingRect() {
        return rect;
    }

    /**
     * Metodo getter che permette di ottenere il nome dell'esperienza.
     *
     * @return Restituisce il nome dell'esperienza.
     */

    public String getName() {
        return name;
    }

    /**
     * Metodo getter che permette di ottenere l'id dell'esperienza.
     *
     * @return Restituisce l'ud dell'esperienza.
     */

    public String getId() {
        return id;
    }

    /**
     * Metodo getter che permette di ottenere l'insieme dei punti di interesse dell'esperienza.
     *
     * @return Restituisce l'insieme dei punti di interesse dell'esperienza.
     */

    @JsonGetter("points_of_interest")
    public Iterable<PointOfInterest> getPOIs() {
        return pois;
    }

    /**
     * Metodo getter che permette di ottenere l'insieme dei quadranti raster
     * associati all'esperienza.
     *
     * @return Restituisce l'insieme dei quadranti raster associati all'esperienza.
     */

    public Iterable<RasterQuadrant> getRasterData() {
        return Renderer.fromExperience(this).getRasterQuadrants();
    }

}
