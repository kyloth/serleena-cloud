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

import java.util.Arrays;

/**
 * Classe che concretizza IExperience.
 *
 * @use Utilizza Render.Renderer per creare in modo lazy i quadranti raster
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Experience {

    /**
     * Insieme dei Percorsi relativi all'Esperienza.
     */

    private Iterable<Track> tracks;

    /**
     * Insieme dei Punti Utente relativi all'Esperienza.
     */

    private Iterable<UserPoint> userPoints;

    /**
     * Insieme dei Punti di Interesse relativi all'Esperienza.
     */

    private Iterable<PointOfInterest> pois;

    /**
     * Rappresenta l'area geografica in cui è situata l'Esperienza.
     */

    private Rect rect;

    /**
     * Nome dell'Esperienza.
     */

    private String name;

    /**
     * Crea un oggetto Experience inizializzandone i campi dati.
     *
     * @param name Il nome dell'Esperienza.
     * @param rect L'area geografica in cui è situata l'Esperienza.
     * @param tracks L'insieme dei Percorsi relativi all'Esperienza.
     * @param userPoints L'insieme dei Punti Utente relativi all'Esperienza.
     * @param pois L'insieme dei Punti di Interesse relativi all'Esperienza.
     */

    public Experience(String name, Rect rect, Iterable<Track> tracks, Iterable<UserPoint> userPoints, Iterable<PointOfInterest> pois) {
        this.name = name;
        this.rect = rect;
        this.tracks = tracks;
        this.userPoints = userPoints;
        this.pois = pois;
    }

    /**
     * Crea un oggetto Experience inizializzandone i campi dati.
     *
     * @param name Il nome dell'Esperienza.
     * @param rect L'area geografica in cui è situata l'Esperienza.
     * @param tracks L'array dei Percorsi relativi all'Esperienza.
     * @param userPoints L'array dei Punti Utente relativi all'Esperienza.
     * @param pois L'array dei Punti di Interesse relativi all'Esperienza.
     */

    public Experience(String name, Rect rect, Track[] tracks, UserPoint[] userPoints, PointOfInterest[] pois) {
        this.name = name;
        this.rect = rect;
        this.tracks = Arrays.asList(tracks);
        this.userPoints = Arrays.asList(userPoints);
        this.pois = Arrays.asList(pois);
    }

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Percorsi relativi all'Esperienza.
     *
     * @return Restituisce l'insieme dei percorsi relativi all'Esperienza.
     */

    public Iterable<Track> getTracks() {
        return tracks;
    }

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Punti Utente relativi all'Esperienza.
     *
     * @return Restituisce l'insieme dei Punti Utente relativi all'Esperienza.
     */

    public Iterable<UserPoint> getUserPoints() {
        return userPoints;
    }

    /**
     * Metodo "getter" che permette di ottenere un oggetto di tipo IRect che rappresenta l'area geografica relativa all'Esperienza.
     *
     * @return Restituisce un IRect che rappresenta l'area geografica dell'Esperienza.
     */

    public Rect getBoundingRect() {
        return rect;
    }

    /**
     * Metodo "getter" che permette di ottenere il nome dell'Esperienza.
     *
     * @return Restituisce il nome dell'Esperienza.
     */

    public String getName() {
        return name;
    }

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Punti di Interesse dell'Esperienza.
     *
     * @return Restituisce l'insieme dei Punti di Interesse dell'Esperienza.
     */

    public Iterable<PointOfInterest> getPOIs() {
        return pois;
    }

    public Iterable<RasterQuadrant> getRasterData() {
        return Renderer.fromExperience(this).getRasterQuadrants();
    }

}
