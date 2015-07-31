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
 * Name: SyncInputData.java
 * Package: com.kyloth.serleenacloud.datamodel.sync
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.sync;

import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Classe che modella i dati di sincronizzazione in input.
 *
 * @use Aggrega tutte le informazioni inviate dall'applicativo Android durante la sincronizzazione, viene utilizzata da DataRestController. I dati verranno inseriti nella base di dati contemporaneamente alla creazione dell'oggetto.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class SyncInputData {

    /**
     * Insieme di Punti Utente relativi all'Esperienza da sincronizzare.
     */

    private Iterable<UserPoint> userPoints;

    /**
     * Insieme di dati telemetrici relativi all'Esperienza da sincronizzare.
     */

    private Iterable<Telemetry> telemetryData;

    /**
     * Id dell'Esperienza da sincronizzare.
     */

    private String experienceId;

    /**
     * Crea un nuovo oggetto SyncInputData per una specifica Esperienza
     * da sincronizzare.
     *
     * @param experienceId Id dell'esperienza da sincronizzare.
     * @param userPoints Insieme dei Punti Utente da sincronizzare.
     * @param telemetryData Insieme dei dati di telemetria da sincronizzare.
     */

    public SyncInputData(String experienceId, Iterable<UserPoint> userPoints, Iterable<Telemetry> telemetryData) {
        this.experienceId = experienceId;
        this.telemetryData = telemetryData;
        this.userPoints = userPoints;
    }

    /**
     * Crea un nuovo oggetto SyncInputData per una specifica Esperienza
     * da sincronizzare.
     *
     * @param experienceId Id dell'esperienza da sincronizzare.
     * @param userPoints Array dei Punti Utente da sincronizzare.
     * @param telemetryData Array dei dati di telemetria da sincronizzare.
     */

    @JsonCreator
    public SyncInputData(@JsonProperty("experience") String experienceId,
                         @JsonProperty("userPoints") UserPoint[] userPoints,
                         @JsonProperty("telemetryData") Telemetry[] telemetryData) {
        this.experienceId = experienceId;
        this.telemetryData = Arrays.asList(telemetryData);
        this.userPoints = Arrays.asList(userPoints);
    }

    /**
     * Metodo getter per ottenere l'id dell'Esperienza da sincronizzare.
     *
     * @return Restituisce l'id dell'Esperienza da sincronizzare.
     */

    public String getExperienceId() {
        return experienceId;
    }

    /**
     * Metodo getter per ottenere l'insieme dei Punti Utente da sincronizzare.
     *
     * @return Restituisce l'insieme dei Punti Utente da sincronizzare.
     */

    public Iterable<UserPoint> getUserPoints() {
        return userPoints;
    }

    /**
     * Metodo getter per ottenere l'insieme dei dati di tracciamento da sincronizzare.
     *
     * @return Restituisce l'insieme dei dati di tracciamento da sincronizzare.
     */

    public Iterable<Telemetry> getTelemetryData() {
        return telemetryData;
    }

}
