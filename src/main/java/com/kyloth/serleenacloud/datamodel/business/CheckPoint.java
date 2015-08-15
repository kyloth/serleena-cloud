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
 * Classe che rappresenta un checkpoint in un percorso.
 *
 * @use Viene riferita da ITrack nella collezione di punti utente di ogni percorso
 * @field id : int Id unico per il checkpoint
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class CheckPoint extends Point {

    /**
     * Id del checkpoint.
     */

    int id;

    /**
     * Crea un nuovo oggetto checkpoint.
     *
     * @param latitude La latitude del checkpoint.
     * @param longitude La longitudine del checkpoint.
     * @param id L'id del checkpoint.
     */

    @JsonCreator
    public CheckPoint(@JsonProperty("latitude") double latitude,
                      @JsonProperty("longitude") double longitude,
                      @JsonProperty("id") int id) {
        super(latitude, longitude);
        this.id = id;
    }

    /**
     * Metodo getter per ottenere l'id del checkpoint.
     *
     * @return Restituisce l'id del checkpoint.
     */

    public int getId() {
        return id;
    }
}
