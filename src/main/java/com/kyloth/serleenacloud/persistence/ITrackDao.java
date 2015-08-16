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
 * Name: ITrackDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.business.Track;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo Track.
 *
 * @use Contiene metodi per cercare e inserire percorsi nella base di dati.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface ITrackDao {

    /**
     * Permette di inserire un nuovo percorso nella base di dati.
     *
     * @param e Il percorso da inserire.
     */

    public void persist(Track e);

    /**
     * Permette di ottenere un percorso presente nella base di dati a partire
     * dal nome.
     *
     * @param name Nome del percorso da ottenere.
     */

    public Track find(String name);

    /**
     * Permette di ottenere una lista di percorsi per una determinata esperienza.
     *
     * @param experienceId Id dell'Esperienza per la quale si vogliono ottenere i percorsi.
     * @return Restituisce la lista dei percorsi relativi all'esperienza specificata.
     */

    public Iterable<Track> findAll(String experienceId);

    /**
     * Permette di ottenere tutti i percorsi salvati nella base di dati.
     *
     * @return Restituisce la lista dei percorsi.
     */

    public Iterable<Track> findAll();
}
