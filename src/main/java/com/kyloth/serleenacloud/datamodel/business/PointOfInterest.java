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
 * Name: PointOfInterest.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.Point;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Classe che rappresenta un punto di interesse nella mappa.
 *
 * @use Viene utilizzata da Render.Renderer durante la creazione dei quadranti raster. Rende disponibile un metodo che ritorna un valore di un enum che identifica il tipo di punto di interesse
 * @field name : String Nome del punto di interesse
 * @field type : POIType Categoria del punto di interesse
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class PointOfInterest extends Point {

    /**
     * Enumerazione per le diverse possibili categorie di punti di interesse.
     */

    public static enum POIType {
        FOOD, INFO, WARNING;

        @JsonCreator
        public POIType fromJson(String value) {
            return POIType.valueOf(value);
        }
    }

    /**
     * Nome del punto di interesse.
     */

    String name;

    /**
     * Categoria del punto di interesse.
     */

    POIType type;

    /**
     * Crea un nuovo punto di interesse inizializzandone i campi dati.
     *
     * @param latitude La latitudine del punto di interesse.
     * @param longitude La longitudine del punto di interesse.
     * @param name Il nome del punto di interesse.
     * @param type La categoria del punto di interesse.
     */

    @JsonCreator
    public PointOfInterest(@JsonProperty("latitude") double latitude,
                           @JsonProperty("longitude") double longitude,
                           @JsonProperty("name") String name,
                           @JsonProperty("type") POIType type) {
        super(latitude, longitude);
        this.name = name;
        this.type = type;
    }

    /**
     * Metodo getter per ottenere il nome del punto di interesse.
     *
     * @return Restituisce il nome del punto di interesse.
     */

    public String getName() {
        return name;
    }

    /**
     * Metodo getter per ottenere la categoria del punto di interesse.
     *
     * @return Restituisce la categoria del punto di interesse.
     */

    @JsonGetter("type")
    public POIType getPOIType() {
        return type;
    }
}
