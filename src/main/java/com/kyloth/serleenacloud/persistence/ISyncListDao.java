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
 * Name: ISyncListDao.java
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
 * Interfaccia implementata da una classe che realizza la persistenza su database della lista delle esperienze da sincronizzare.
 *
 * @use Contiene metodi per cercare e inserire nuove esperienze nella lista di sincronizzazione della base di dati.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface ISyncListDao {
    
    /**
     * Permette di inserire una nuova Esperienza nella lista di
     * sincronizzazione.
     *
     * @param es L'Esperienza da inserire nella lista.
     */

    public void persist(Iterable<Experience> es);
    
    /**
     * Permette di ottenere tutte le Esperienze in lista di
     * sincronizzazione.
     *
     * @return Restituisce la lista delle Esperienze in lista di sincronizzazione.
     */

    public Iterable<Experience> findAll();
}
