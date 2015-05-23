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
 * Name: CheckPoint.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.kyloth.serleenacloud.datamodel.geometry.Point;

/**
 * Classe che rappresenta un Checkpoint in un Percorso.
 *
 * @use Viene riferita da ITrack nella collezione di Punti Utente di ogni Percorso
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class CheckPoint extends Point {

    /**
     * Id del Checkpoint.
     */

    int id;

    /**
     * Crea un nuovo oggetto Checkpoint.
     *
     * @param latitude La latitude del Checkpoint.
     * @param longitude La longitudine del Checkpoint.
     * @param id L'id del Checkpoint.
     */

    @JsonCreator
    public CheckPoint(@JsonProperty("latitude") double latitude,
                      @JsonProperty("longitude") double longitude,
                      @JsonProperty("id") int id) {
        super(latitude, longitude);
        this.id = id;
    }

    /**
     * Metodo "getter" per ottenere l'id del Checkpoint.
     *
     * @return Restituisce l'id del Checkpoint.
     */

    public int getId() {
        return id;
    }
}
