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
 * Name: IExperienceDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.business.Experience;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo Experience.
 *
 * @use Contiene metodi per cercare e inserire esperienze nella base di dati.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IExperienceDao {

    /**
     * Permette di inserire una nuova esperienza nella base di dati.
     *
     * @param e Esperienza da inserire.
     */

    public void persist(Experience e);

    /**
     * Restituisce tutte le esperienze relative all'utente corrente.
     *
     * @return Restituisce la lista delle esperienze relative all'utente.
     */

    public Iterable<Experience> findAll();

    /**
     * Permette di ottenere una particolare esperienza.
     *
     * @param id Id dell'esperienza da ottenere.
     * @return Restituisce l'esperienza cercata, se presente.
     */

    public Experience find(String id);

    /**
     * Permette di eliminare un'esperienza dalla base di dati.
     *
     * @param id Id dell'esperienza da eliminare.
     */

    public void delete(String id);
}
