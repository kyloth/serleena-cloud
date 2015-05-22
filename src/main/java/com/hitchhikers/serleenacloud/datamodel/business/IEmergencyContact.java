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
 * Name: IEmergencyContact.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.IRect;

/**
 * Interfaccia che rappresenta informazioni riguardanti contatti di emergenza
 * disponibili in un'area di mappa.
 *
 * @use Viene utilizzata da DataRestController per creare JSON richiesto dall'applicazione android
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IEmergencyContact {

    /**
     * Metodo "getter" che permette di ottenere un oggetto IRect che rappresenta un'area geografica.
     *
     * @return Restituisce un oggetto IRect che rappresenta l'area geografica relativa al contatto di emergenza.
     */

    public IRect getBoundingRect();

    /**
     * Metodo "getter" che permette di ottenere il nome del contatto di emergenza.
     *
     * @return Restituisce il nome del contatto di emergenza.
     */

    public String getName();

    /**
     * Metodo "getter" che permette di ottenere il numero del contatto di emergenza.
     *
     * @return Restituisce il numero del contatto di emergenza.
     */

    public String getNumber();
}
