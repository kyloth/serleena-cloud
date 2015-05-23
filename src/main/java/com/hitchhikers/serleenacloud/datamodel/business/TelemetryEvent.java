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
 * Name: TelemetryEvent.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe che rappresenta un evento di Tracciamento.
 *
 * @use Viene utilizzata da ITrack durante il calcolo del tracciamento migliore durante la creazione del JSON da condividere con l'applicazione android
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class TelemetryEvent {

    /**
     * Enumerazione delle diverse possibili categorie di evento di Tracciamento.
     */

    public static enum EventType {
        CHECKPOINT, HEART;

        @JsonCreator
        public EventType fromJson(String value) {
            return EventType.valueOf(value);
        }

    }

    /**
     * Categoria dell'evento di Tracciamento.
     */

    private EventType type;

    /**
     * Valore dell'evento di Tracciamento.
     */

    private double value;

    /**
     * Timestamp dell'evento di Tracciamento.
     */

    private Date time;

    /**
     * Crea un nuovo oggetto TelemetryEvent inizializzandone i campi dati.
     *
     * @param type Categoria dell'evento di Telemetria.
     * @param time Timestamp dell'evento di Telemetria.
     * @param value Valore dell'evento di Telemetria.
     */

    @JsonCreator
    public TelemetryEvent(@JsonProperty("type") EventType type,
                          @JsonProperty("time") Date time,
                          @JsonProperty("value") double value) {
        this.type = type;
        this.time = time;
        this.value = value;
    }

    /**
     * Metodo "getter" per ottenere la categoria dell'evento di Telemetria.
     *
     * @return Restituisce la categoria dell'evento di Telemetria.
     */

    public EventType eventType() {
        return type;
    }

    /**
     * Metodo "getter" per ottenere il valore dell'evento di Telemetria.
     *
     * @return Restituisce il valore dell'evento di Telemetria.
     */

    public double getValue() {
        return value;
    }

    /**
     * Metodo "getter" per ottenere il timestamp dell'evento di Telemetria.
     *
     * @return Restituisce il timestamp dell'evento di Telemetria.
     */

    public Date getTime() {
        return time;
    }

}
