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

import com.kyloth.serleenacloud.datamodel.geometry.Rect;

/**
 * Classe che rappresenta informazioni riguardanti contatti di emergenza
 * disponibili in un'area di mappa.
 *
 * @use Viene utilizzata da DataRestController per creare JSON richiesto dall'applicazione android
 * @field boundingRect : Rect Oggetto rappresentante l'area geografica relativa al contatto di emergenza
 * @field name : String Nome del contatto di emergenza
 * @field number : String Numero per contattare il contatto di emergenza
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class EmergencyContact {

    /**
     * Rappresenta l'area geografica relativa al contatto di emergenza.
     */

    private Rect boundingRect;

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

    public EmergencyContact(String name, Rect boundingRect, String number) {
        this.name = name;
        this.boundingRect = boundingRect;
        this.number = number;
    }

    /**
     * Metodo getter che permette di ottenere l'area geografica relativa al contatto.
     *
     * @return Restituisce un oggetto di tipo Rect che rappresenta l'area geografica relativa al contatto.
     */

    public Rect getBoundingRect() {
        return boundingRect;
    }

    /**
     * Metodo getter che permette di ottenere il numero del contatto.
     *
     * @return Restituisce il numero del contatto.
     */

    public String getNumber() {
        return number;
    }

    /**
     * Metodo getter che permette di ottenere il nome del contatto.
     *
     * @return Restituisce il nome del contatto.
     */

    public String getName() {
        return name;
    }
}
