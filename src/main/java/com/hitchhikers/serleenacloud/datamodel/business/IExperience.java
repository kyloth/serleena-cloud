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
 * Name: IExperience.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.IRect;
//import com.kyloth.serleenacloud.render.IRasterQuadrant;

/**
 * Interfaccia che rappresenta un'Esperienza.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IExperience {

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Percorsi relativi all'Esperienza.
     *
     * @return Restituisce l'insieme dei percorsi relativi all'Esperienza.
     */

    public Iterable<ITrack> getTracks();

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Punti Utente relativi all'Esperienza.
     *
     * @return Restituisce l'insieme dei Punti Utente relativi all'Esperienza.
     */

    public Iterable<UserPoint> getUserPoints();

    /**
     * Metodo "getter" che permette di ottenere un oggetto di tipo IRect che rappresenta l'area geografica relativa all'Esperienza.
     *
     * @return Restituisce un IRect che rappresenta l'area geografica dell'Esperienza.
     */

    public IRect getBoundingRect();

    /**
     * Metodo "getter" che permette di ottenere il nome dell'Esperienza.
     *
     * @return Restituisce il nome dell'Esperienza.
     */

    public String getName();

    /**
     * Metodo "getter" che permette di ottenere l'insieme dei Punti di Interesse dell'Esperienza.
     *
     * @return Restituisce l'insieme dei Punti di Interesse dell'Esperienza.
     */

    public Iterable<PointOfInterest> getPOIs();
    //    public Iterable<IRasterQuadrant> getRasterData();
}
