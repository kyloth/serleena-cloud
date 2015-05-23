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

/**
 * Classe che concretizza ITelemetry.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Telemetry {

    /**
     * Insieme degli eventi associati al Tracciamento.
     */

    Iterable<TelemetryEvent> events;

    /**
     * Traccia a cui è associato il Tracciamento.
     */

    String track;

    /**
     * Crea un nuovo oggetto Telemetry inizializzandone i campi dati.
     *
     * @param events Insieme degli eventi associati al Tracciamento.
     * @param track Traccia a cui è associato il Tracciamento.
     */

    public Telemetry(Iterable<TelemetryEvent> events, String track) {
        this.events = events;
        this.track = track;
    }

    /**
     * Crea un nuovo oggetto Telemetry inizializzandone i campi dati.
     *
     * @param events Array degli eventi associati al Tracciamento.
     * @param track Traccia a cui è associato il Tracciamento.
     */

    public Telemetry(TelemetryEvent[] events, String track) {
        this.events = Arrays.asList(events);
        this.track = track;
    }

    /**
     * Metodo "getter" che permette di ottenere l'insieme degli eventi associati al Tracciamento.
     *
     * @return Restituisce l'insieme degli eventi associati al Tracciamento.
     */

    public Iterable<TelemetryEvent> getEvents() {
        return events;
    }

    /**
     * Metodo "getter" che permette di ottenere la traccia a cui è associato il tracciamento
     *
     * @return Restituisce la traccia a cui è associato il tracciamentoTracciamento.
     */

    public String getTrack() {
        return track;
    }

    /**
     * Confronta due oggetti Telemetry sulla base del tempo totale registrato.
     *
     * @param telemetry Il Tracciamento contro il quale effettuare il confronto.
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
     * Calcola il tempo totale registrato per un dato Tracciamento, in particolare tale durata viene calcolata in base ai timestamp degli eventi di tipo CHECKPOINT.
     *
     * @param telemetry Il Tracciamento di cui calcolare il tempo totale.
     * @return Restituisce il tempo totale registrato.
     */

    private static long duration (Telemetry telemetry) {
        long duration = 0;
        Date lastDate = null;

        for (TelemetryEvent event : telemetry.getEvents())
            if (event.eventType() == TelemetryEvent.EventType.CHECKPOINT) {
                if (lastDate != null)
                    duration += event.getTime().getTime() - lastDate.getTime();
                lastDate = event.getTime();
            }
        return duration;

    }
}
