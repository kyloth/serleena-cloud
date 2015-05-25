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
 * Name: ITelemetryDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.business.Telemetry;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo Telemetry.
 *
 * @use Contiene metodi per cercare e inserire dati riguardanti i tracciamenti nella base di dati.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface ITelemetryDao {
    
    /**
     * Permette di inserire un nuovo tracciamento nella base di dati.
     *
     * @param trackName Nome del percorso cui è relativo il tracciamento.
     * @param t Tracciamento da inserire.
     */

    public void persist(String trackName, Telemetry t);
    
    /**
     * Permette di ottenere tutti i tracciamenti per un certo percorso.
     *
     * @param trackName Nome del percorso per il quale si vuole ottenere la lista di tracciamenti.
     * @return Restituisce la lista dei tracciamenti per il percorso specificato.
     */

    public Iterable<Telemetry> findAll(String trackName);
}
