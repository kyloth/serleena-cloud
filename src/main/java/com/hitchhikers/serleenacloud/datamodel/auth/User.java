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
 * Name: User.java
 * Package: com.kyloth.serleenacloud.datamodel.auth
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.auth;

/**
 * Classe che modella un utente del portale
 *
 * @use Interagisce con AuthToken per la gestione del token di autenticazione
 * @field deviceId : String Id del dispositivo posseduto dall'utente
 * @field email : String Indirizzo email dell'utente
 * @field password : String Password dell'utente
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class User {

    /**
     * Id del dispositivo associato all'utente.
     */

    private String deviceId;

    /**
     * Indirizzo email dell'utente.
     */

    private String email;

    /**
     * Password dell'utente.
     */

    private String password;

    /**
     * Crea un nuovo oggetto User impostando i campi dati secondo
     * i parametri forniti.
     *
     * @param email L'email dell'utente.
     * @param password La password fornita dall'utente.
     * @param deviceId L'id del dispositivo associato all'utente.
     */

    public User(String email, String password, String deviceId) {
        this.deviceId = deviceId;
        this.email = email;
        this.password = password;
    }

    /**
     * Crea un nuovo token di autenticazione per l'utente.
     *
     * @return Restituisce un AuthToken costruito a partire dai dati dell'utente.
     */

    public AuthToken getAuthToken() {
        return new AuthToken(this);
    }

    /**
     * Metodo getter per ottenere l'indirizzo email dell'utente.
     *
     * @return Restituisce l'indirizzo email dell'utente.
     */

    public String getEmail() {
        return email;
    }

    /**
     * Metodo getter per ottenere la password dell'utente.
     *
     * @return Restituisce la password dell'utente.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Metodo getter per ottenere l'id del dispositivo associato all'utente.
     *
     * @return Restituisce l'id del dispositivo associato all'utente.
     */

    public String getDeviceId() {
        return deviceId;
    }
    
    /**
     * Confronta due utenti e stabilisce se sono uguali.
     *
     * @param u L'utente con il quale si vuole effettuare il confronto.
     * @return True se i due utenti sono uguali, False altrimenti.
     */

    public boolean equals(User u) {
        return u.getEmail().equals(email)
            && u.getPassword().equals(password);
    }
}
