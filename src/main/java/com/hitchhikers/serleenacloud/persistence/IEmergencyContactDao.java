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
 * Name: IEmergencyContactDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo EmergencyContact.
 *
 * @use Contiene metodi per cercare e inserire dati riguardanti i contatti di emergenza nella base di dati.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IEmergencyContactDao {
    
    /**
     * Permette di ottenere la lista di tutti i contatti di emergenza
     * relativi a una particolare regione di mappa.
     *
     * @param region La regione di mappa di interesse.
     * @return Restituisce la lista dei contatti di emergenza relativi alla regione specificata.
     */

    public Iterable<EmergencyContact> findAll(Rect region);
}
