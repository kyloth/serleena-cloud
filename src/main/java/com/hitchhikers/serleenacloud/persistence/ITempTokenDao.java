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
 * Name: ITempTokenDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.auth.TempToken;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo TempoToken.
 *
 * @use Contiene metodi per inserire un token temporaneo per un determinato dispositivo e ottenere l'id di questo dispositivo a partire dal token.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface ITempTokenDao {
    
    /**
     * Permette di inserire un nuovo token temporaneo nella base di dati.
     *
     * @param token Il token da inserire.
     */

    public void persist(TempToken token);
    
    /**
     * Permette di ottenere l'id del dispositivo al quale è associato un particolare token temporaneo.
     *
     * @param token Il token per il quale si vuole ottenere l'id del dispositivo.
     * @return Restituisce l'id del dispositivo associato al token passato come parametro.
     */

    public String deviceId(String token);
}
