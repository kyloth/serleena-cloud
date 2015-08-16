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
 * Name: IUserDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.auth.User;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo User.
 *
 * @use Contiene metodi per cercare e inserire nuovi utenti nella base di dati.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IUserDao {
    
    /**
     * Permette di inserire un nuovo utente nella base di dati.
     * 
     * @param token Utente da inserire.
     */

    public void persist(User token);
    
    /**
     * Permette di ottenere un utente a partire dalla sua email.
     *
     * @param email Email dell'utente che si vuole ottenere.
     * @return Restituisce l'utente cercato, se presente.
     */

    public User find(String email);
    
    /**
     * Permette di ottenere un utente a partire dall'id di un dispositivo.
     *
     * @param deviceId L'id del dispositivo a partire dal quale si vuole cercare l'utente.
     * @return Restituisce l'utente cercato, se presente.
     */

    public User findDeviceId(String deviceId);
}
