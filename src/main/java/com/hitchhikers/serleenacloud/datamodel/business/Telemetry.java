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
 * Name: Telemetry.java
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
 * Classe che rappresenta informazioni di Tracciamento.
 *
 * @use Viene utilizzata da DataRestController e ExperienceRestController per creare o elaborare il JSON fornito o richiesto da frontend e applicazione android
 * @field events : Iterable<Date> Insieme dei timestamp degli eventi associati al tracciamento
 * @field track : String Nome del percorso a cui è associato il tracciamento
 * @field id : String Identificativo univoco per il tracciamento
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Telemetry {

    /**
     * Insieme dei timestamp degli eventi associati al tracciamento.
     */

    Iterable<Date> events;

    /**
     * Percorso a cui è associato il tracciamento.
     */

    String track;

    /**
     * Identificativo del tracciamento.
     */

    String id;

    /**
     * Crea un nuovo oggetto Telemetry inizializzandone i campi dati.
     *
     * @param events Insieme dei timestamp degli eventi associati al tracciamento.
     * @param track percorso a cui è associato il tracciamento.
     */

    public Telemetry(Iterable<Date> events, String track) {
        this.events = events;
        this.track = track;
    }

    /**
     * Crea un nuovo oggetto Telemetry inizializzandone i campi dati.
     *
     * @param events Insieme dei timestamp degli eventi associati al tracciamento.
     * @param track percorso a cui è associato il tracciamento.
     * @param track Identificativo del tracciamento.
     */

    public Telemetry(Iterable<Date> events, String track, String id) {
        this.events = events;
        this.track = track;
        this.id = id;
    }


    /**
     * Crea un nuovo oggetto Telemetry inizializzandone i campi dati.
     *
     * @param events Array dei timestamp degli eventi associati al tracciamento.
     * @param track Percorso a cui è associato il tracciamento.
     */

    @JsonCreator
    public Telemetry(@JsonProperty("events") Date[] events,
                     @JsonProperty("track") String track) {
        this.events = Arrays.asList(events);
        this.track = track;
    }

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei timestamp degli eventi associati al tracciamento.
     *
     * @return Restituisce l'insieme dei timestamp degli eventi associati al tracciamento.
     */

    public Iterable<Date> getEvents() {
        return events;
    }

    /**
     * Metodo getter che permette di ottenere il percorso a cui è associato il tracciamento
     *
     * @return Restituisce il percorso a cui è associato il tracciamento.
     */

    public String getTrack() {
        return track;
    }

    /**
     * Metodo getter che permette di ottenere l'identificativo del tracciamento
     *
     * @return Restituisce l'identificativo del tracciamento
     */

    public String getId() {
        return (id == null ? "" : id);
    }

    /**
     * Confronta due oggetti Telemetry sulla base del tempo totale registrato.
     *
     * @param telemetry Il tracciamento contro il quale effettuare il confronto.
     * @return Restituisce 0 se i due oggetti hanno lo stesso tempo totale registrato, 1 se l'oggetto su cui è invocato il metodo ha tempo totale maggiore, -1 altrimenti.
     */

    public int compareTo(Telemetry telemetry) {
        long thisDuration = duration(this);
        long othDuration = duration(telemetry);
        if (thisDuration == othDuration)
            return 0;
        else if (thisDuration > othDuration)
            return 1;
        else
            return -1;
    }

    /**
     * Calcola il tempo totale registrato per un dato tracciamento.
     *
     * @param telemetry Il tracciamento di cui calcolare il tempo totale.
     * @return Restituisce il tempo totale registrato.
     */

    private static long duration (Telemetry telemetry) {
        long duration = 0;
        Date lastDate = null;

        for (Date event : telemetry.getEvents()) {
            if (lastDate != null)
                duration += event.getTime() - lastDate.getTime();
            lastDate = event;
        }
        return duration;
    }
}
