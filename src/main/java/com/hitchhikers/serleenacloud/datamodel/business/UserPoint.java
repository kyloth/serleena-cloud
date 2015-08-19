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
 * Name: UserPoint.java
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe che rappresente un punto utente in un'esperienza.
 *
 * @use Viene riferita da Experience nella collezione di punti utente di ogni esperienza
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPoint extends Point {

    @JsonCreator
    public UserPoint(@JsonProperty("latitude") double latitude,
                     @JsonProperty("longitude") double longitude) {
        super(latitude, longitude);
    }

    public String getName() {
        return "";
    }
}
