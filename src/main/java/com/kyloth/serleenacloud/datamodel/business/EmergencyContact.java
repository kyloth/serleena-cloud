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
 * Name: EmergencyContact.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.IRect;

import java.util.Date;

/**
 * Classe che concretizza IEmergencyContact.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class EmergencyContact implements IEmergencyContact {

    /**
     * Rappresenta l'area geografica relativa al contatto di emergenza.
     */

    private IRect boundingRect;

    /**
     * Nome del contatto di emergenza.
     */

    private String name;

    /**
     * Numero del contatto di emergenza.
     */

    private String number;

    /**
     * Crea un oggetto EmergencyContact inizializzandone i campi dati.
     *
     * @param name Il nome del contatto.
     * @param boundingRect L'area geografica relativa al contatto.
     * @param number Il numero del contatto.
     */

    public EmergencyContact(String name, IRect boundingRect, String number) {
        this.name = name;
        this.boundingRect = boundingRect;
        this.number = number;
    }

    /**
     * Metodo "getter" che permette di ottenere l'area geografica relativa al contatto.
     *
     * @return Restituisce un oggetto di tipo IRect che rappresenta l'area geografica relativa al contatto.
     */

    public IRect getBoundingRect() {
        return boundingRect;
    }

    /**
     * Metodo "getter" che permette di ottenere il numero del contatto.
     *
     * @return Restituisce il numero del contatto.
     */

    public String getNumber() {
        return number;
    }

    /**
     * Metodo "getter" che permette di ottenere il nome del contatto.
     *
     * @return Restituisce il nome del contatto.
     */

    public String getName() {
        return name;
    }
}
