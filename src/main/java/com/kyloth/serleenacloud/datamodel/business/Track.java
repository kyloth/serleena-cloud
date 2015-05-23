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
 * Name: Track.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import java.util.Arrays;

/**
 * Classe che implementa ITrack.
 *
 * @use Viene utilizzata da Render.Renderer durante la creazione dei quadranti raster
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Track {

    /**
     * Il nome del Percorso.
     */

    private String name;

    /**
     * L'insieme dei Checkpoint del Percorso.
     */

    private Iterable<CheckPoint> checkpoints;

    /**
     * L'insieme dei Tracciamenti relativi al Percorso.
     */

    private Iterable<Telemetry> telemetries;

    /**
     * Crea un nuovo Percorso inizializzandone i campi dati.
     *
     * @param name Il nome del Percorso.
     * @param checkpoints Insieme dei Checkpoint del Percorso.
     * @param telemetries Insieme dei Tracciamenti relativi al Percorso.
     */

    public Track(String name, Iterable<CheckPoint> checkpoints, Iterable<Telemetry> telemetries) {
        this.name = name;
        this.checkpoints = checkpoints;
        this.telemetries = telemetries;
    }

    /**
     * Crea un nuovo Percorso inizializzandone i campi dati.
     *
     * @param name Il nome del Percorso.
     * @param checkpoints Array dei Checkpoint del Percorso.
     * @param telemetries Array dei Tracciamenti relativi al Percorso.
     */

    public Track(String name, CheckPoint[] checkpoints, Telemetry[] telemetries) {
        this.name = name;
        this.checkpoints = Arrays.asList(checkpoints);
        this.telemetries = Arrays.asList(telemetries);
    }

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Checkpoint associati al Percorso.
     *
     * @return Restituisce l'insieme dei Checkpoint associati al Percorso.
     */

    public Iterable<CheckPoint> getCheckPoints() {
        return checkpoints;
    }

    /**
     * Metodo "getter" che permette di ottenere il nome del Percorso.
     *
     * @return Restituisce il nome del Percorso.
     */

    public String getName() {
        return name;
    }

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei dati di Tracciamento relativi al Percorso.
     *
     * @return Restituisce l'insieme dei dati di Tracciamento relativi al Percorso.
     */

    public Iterable<Telemetry> getTelemetries() {
        return telemetries;
    }

    /**
     * Metodo che permette di ottenere il miglior Tracciamento disponibile per il Percorso.
     *
     * @return Restituisce il miglior Tracciamento disponibile per il Percorso.
     */

    public Telemetry getBestTelemetry() {
        Telemetry best = null;
        for (Telemetry telemetry : getTelemetries())
            if (best == null || best.compareTo(telemetry) > 0)
                best = telemetry;
        return best;
    }
}
