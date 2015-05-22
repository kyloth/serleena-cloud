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

/**
 * Classe che rappresente un Punto Utente in un'Esperienza.
 *
 * @use Viene riferita da IExperience nella collezione di Punti Utente di ogni esperienza
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class UserPoint extends Point {

    /**
     * Nome del Punto Utente.
     */

    String name;

    /**
     * Crea un nuovo Punto Utente inizializzandone i campi dati.
     *
     * @param latitude Latitudine del Punto Utente.
     * @param longitude Longitudine del Punto Utente.
     * @param name Nome del Punto Utente.
     */

    public UserPoint(double latitude, double longitude, String name) {
        super(latitude, longitude);
        this.name = name;
    }

    /**
     * Metodo "getter" per ottenere il nome del Punto Utente.
     *
     * @return Restituisce il nome del Punto Utente.
     */

    public String getName() {
        return name;
    }
}
