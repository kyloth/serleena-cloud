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
import com.kyloth.serleenacloud.datamodel.business.ITelemetry;

import java.util.Arrays;

/**
 * Classe che modella i dati di sincronizzazione in input.
 * In particolare ne verrà creata un'istanza per ogni Esperienza da sincronizzare.
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

    private Iterable<ITelemetry> telemetryData;

    /**
     * Nome dell'Esperienza da sincronizzare.
     */

    private String experienceName;

    /**
     * Crea un nuovo oggetto SyncInputData per una specifica Esperienza
     * da sincronizzare.
     *
     * @param experienceName Nome dell'esperienza da sincronizzare.
     * @param userPoints Insieme dei Punti Utente da sincronizzare.
     * @param telemetryData Insieme dei dati di telemetria da sincronizzare.
     */

    public SyncInputData(String experienceName, Iterable<UserPoint> userPoints, Iterable<ITelemetry> telemetryData) {
        this.experienceName = experienceName;
        this.telemetryData = telemetryData;
        this.userPoints = userPoints;
    }

    /**
     * Crea un nuovo oggetto SyncInputData per una specifica Esperienza
     * da sincronizzare.
     *
     * @param experienceName Nome dell'esperienza da sincronizzare.
     * @param userPoints Array dei Punti Utente da sincronizzare.
     * @param telemetryData Array dei dati di telemetria da sincronizzare.
     */

    public SyncInputData(String experienceName, UserPoint[] userPoints, ITelemetry[] telemetryData) {
        this.experienceName = experienceName;
        this.telemetryData = Arrays.asList(telemetryData);
        this.userPoints = Arrays.asList(userPoints);
    }

    /**
     * Metodo "getter" per ottenere il nome dell'Esperienza da sincronizzare.
     *
     * @return Restituisce il nome dell'Esperienza da sincronizzare.
     */

    public String getExperienceName() {
        return experienceName;
    }

    /**
     * Metodo "getter" per ottenere l'insieme dei Punti Utente da sincronizzare.
     *
     * @return Restituisce l'insieme dei Punti Utente da sincronizzare.
     */

    public Iterable<UserPoint> getUserPoints() {
        return userPoints;
    }

    /**
     * Metodo "getter" per ottenere l'insieme dei dati di telemetria da sincronizzare.
     *
     * @return Restituisce l'insieme dei dati di telemetria da sincronizzare.
     */

    public Iterable<ITelemetry> getTelemetryData() {
        return telemetryData;
    }

}
