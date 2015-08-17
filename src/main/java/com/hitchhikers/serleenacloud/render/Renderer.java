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
 * Name: Renderer.java
 * Package: com.kyloth.serleenacloud.render
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

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
import com.kyloth.serleenacloud.datamodel.geometry.ElevationRect;

import java.util.ArrayList;

/**
 * Classe per la creazione del rendering delle Esperienze da offrire all'applicativo android.
 *
 * @use Prendendo in input un oggetto Experience, interagisce con le classi DAO del package Persistence per ottenere i dati di mappa circoscritti dall'Esperienza e ritorna una collezione di RasterQuadrant.
 * @field ds : IDataSource Campo dati statico contenente un oggetto che permette di interfacciarsi con il database tramite oggetti DAO
 * @field rect : Rect Rettangolo di coordinate delimitanti l'Esperienza
 * @field elevations : Iterable<ElevationRect> Insieme di ElevationRect disponibili nell'area dell'Esperienza
 * @field lakes : Iterable<Lake> Insieme di laghi disponibili nell'area dell'Esperienza
 * @field rivers : Iterable<River> Insieme di fiumi disponibili nell'area dell'Esperienza
 * @field paths : Iterable<Path> Insieme di sentieri disponibili nell'area dell'Esperienza
 * @field pois : Iterable<PointOfInterest> Insieme di punti d'interesse disponibili nell'area dell'Esperienza
 * @field ups : Iterable<UserPoint> Insieme di punti utente disponibili nell'area dell'Esperienza
 * @field tracks : Iterable<Track> Insieme di percorsi disponibili nell'area dell'Esperienza
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Renderer {

    /**
     * Oggetto che permette di interfacciarsi con il database tramite oggetti DAO.
     */

    static IDataSource ds = DataSourceFactory.getDataSource();

    /**
     * Rettangolo di coordinate delimitanti l'Esperienza.
     */

    Rect rect;

    /**
     *  Insieme di ElevationRect disponibili nell'area dell'Esperienza.
     */

    Iterable<ElevationRect> elevations;

    /**
     * Insieme di laghi disponibili nell'area dell'Esperienza.
     */

    Iterable<Lake> lakes;

    /**
     * Insieme di fiumi disponibili nell'area dell'Esperienza.
     */

    Iterable<River> rivers;

    /**
     * Insieme di sentieri disponibili nell'area dell'Esperienza.
     */

    Iterable<Path> paths;

    /**
     * Insieme di punti d'interesse disponibili nell'area dell'Esperienza.
     */

    Iterable<PointOfInterest> pois;

    /**
     * Insieme di punti utente disponibili nell'area dell'Esperienza.
     */

    Iterable<UserPoint> ups;

    /**
     * Insieme di percorsi disponibili nell'area dell'Esperienza.
     */

    Iterable<Track> tracks;

    /**
     * Costruisce un nuovo Renderer a partire da un'Esperienza.
     *
     * @param e Esperienza a partire dalla quale si vuole ottenere un insieme di RasterQuadrant.
     */

    Renderer(Experience e) {
        this.rect = e.getBoundingRect();
        this.lakes = ds.lakeDao().findAll(rect);
        this.rivers = ds.riverDao().findAll(rect);
        this.elevations = ds.elevationRectDao().findAll(rect);
        this.paths = ds.pathDao().findAll(rect);
        this.pois = e.getPOIs();
        this.ups = e.getUserPoints();
        this.tracks = e.getTracks();
    }

    /**
     * Permette di ottenere un insieme di RasterQuadrant per
     * l'Esperienza relativa al Renderer.
     *
     * @return Restituisce l'insieme di RasterQuadrant per l'Esperienza relativa al Renderer.
     */

    public Iterable<RasterQuadrant> getRasterQuadrants() {
        RasterQuadrant rq = new RasterQuadrant(new ImageRenderer(this));
        ArrayList<RasterQuadrant> quadrants = new ArrayList<RasterQuadrant>();
        while(rq != null) {
            RasterQuadrant curr = rq;
            while(curr != null) {
                quadrants.add(curr);
                curr = curr.getEast();
            }
            rq = rq.getNorth();
        }
        return quadrants;
    }

    /**
     * Permette di ottenere un Renderer per una particolare Esperienza.
     *
     * @param e Esperienza di interesse.
     * @return Restituisce un nuovo Renderer inizializzato a partire dall'Esperienza fornita.
     */

    public static Renderer fromExperience(Experience e) {
        return new Renderer(e);
    }
}
