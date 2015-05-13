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
 * Name: ITrack.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

/**
 * Interfaccia che rappresenta un Percorso.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface ITrack {

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Checkpoint associati al Percorso.
     *
     * @return Restituisce l'insieme dei Checkpoint associati al Percorso.
     */

    public Iterable<CheckPoint> getCheckPoints();

    /**
     * Metodo "getter" che permette di ottenere il nome del Percorso.
     *
     * @return Restituisce il nome del Percorso.
     */

    public String getName();

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei dati di Tracciamento relativi al Percorso.
     *
     * @return Restituisce l'insieme dei dati di Tracciamento relativi al Percorso.
     */

    public Iterable<ITelemetry> getTelemetries();

    /**
     * Metodo che permette di ottenere il miglior Tracciamento disponibile per il Percorso.
     *
     * @return Restituisce il miglior Tracciamento disponibile per il Percorso.
     */

    public ITelemetry getBestTelemetry();
}
