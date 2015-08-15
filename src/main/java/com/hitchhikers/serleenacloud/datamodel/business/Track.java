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
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe che rappresenta un percorso.
 *
 * @use Viene utilizzata da DataRestController e ExperienceRestController per creare o elaborare il JSON fornito o richiesto da frontend e applicazione android. Viene utilizzata da Render.Renderer durante la creazione dei quadranti raster.
 * @field name : String Nome del percorso
 * @field checkpoints : Iterable<CheckPoint> Insieme dei checkpoint associati al percorso
 * @field telemetries : Iterable<Telemetry> Insieme dei tracciamenti associati al percorso
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    /**
     * Il nome del percorso.
     */

    private String name;

    /**
     * L'insieme dei checkpoint del percorso.
     */

    private Iterable<CheckPoint> checkpoints;

    /**
     * L'insieme dei tracciamenti relativi al percorso.
     */

    private Iterable<Telemetry> telemetries;

    /**
     * Crea un nuovo Percorso inizializzandone i campi dati.
     *
     * @param name Il nome del percorso.
     * @param checkpoints Insieme dei checkpoint del percorso.
     * @param telemetries Insieme dei tracciamenti relativi al percorso.
     */

    @JsonCreator
    @SuppressWarnings("unchecked")
    public Track(@JsonProperty("name") String name,
                 @JsonProperty("checkPoints") Iterable<CheckPoint> checkpoints,
                 @JsonProperty("telemetries") Iterable<Telemetry> telemetries) {
        this.name = name;
        this.checkpoints = checkpoints;
        this.telemetries = telemetries != null ? telemetries : Collections.EMPTY_LIST;
    }

    /**
     * Crea un nuovo percorso inizializzandone i campi dati.
     *
     * @param name Il nome del percorso.
     * @param checkpoints Array dei checkpoint del percorso.
     * @param telemetries Array dei tracciamenti relativi al percorso.
     */

    public Track(String name, CheckPoint[] checkpoints, Telemetry[] telemetries) {
        this.name = name;
        this.checkpoints = Arrays.asList(checkpoints);
        this.telemetries = Arrays.asList(telemetries);
    }

    /**
     * Metodo getter che permette di ottenere l'insieme dei checkpoint associati al percorso.
     *
     * @return Restituisce l'insieme dei checkpoint associati al percorso.
     */

    public Iterable<CheckPoint> getCheckPoints() {
        return checkpoints;
    }

    /**
     * Metodo getter che permette di ottenere il nome del percorso.
     *
     * @return Restituisce il nome del percorso.
     */

    public String getName() {
        return name;
    }

    /**
     * Metodo getter che permette di ottenere l'insieme dei dati di tracciamento relativi al percorso.
     *
     * @return Restituisce l'insieme dei dati di tracciamento relativi al percorso.
     */

    @JsonIgnore
    public Iterable<Telemetry> getTelemetries() {
        return telemetries;
    }

    /**
     * Metodo che permette di ottenere il miglior tracciamento disponibile per il percorso.
     *
     * @return Restituisce il miglior tracciamento disponibile per il percorso.
     */

    public Telemetry getBestTelemetry() {
        Telemetry best = null;
        for (Telemetry telemetry : getTelemetries())
            if (best == null || best.compareTo(telemetry) > 0)
                best = telemetry;
        return best;
    }
}
