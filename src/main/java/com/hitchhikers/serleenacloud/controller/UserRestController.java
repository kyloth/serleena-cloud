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
 * Name: UserRestController.java
 * Package: com.kyloth.serleenacloud.controller
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.util.MultiValueMap;

import org.springframework.http.HttpStatus;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.auth.AuthToken;

import java.util.UUID;

/**
 * Controller REST per la gestione delle richieste riguardanti la gestione degli utenti e del pairing utente-dispositivo.
 *
 * @use Risponde alle richieste REST riguardanti la creazione di utenti, pairing tra dispositivo e utente e recupero password.
 * @field ds : IDataSource Campo dati statico contente un oggetto che permette di interfacciarsi con il database tramite DAO
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

@RestController
@RequestMapping("/users")
public class UserRestController {
    
    /**
     * Oggetto che permette di interfacciarsi con il database tramite oggetti DAO.
     */

    static IDataSource ds = DataSourceFactory.getDataSource();

    /**
     * Metodo setter per il DataSource del controller.
     */

    static void setDataSource(IDataSource ds) {
        UserRestController.ds = ds;
    }

    /**
     * Metodo che implementa la richiesta POST per creare un nuovo
     * utente.
     *
     * @param username Email del nuovo utente.
     * @param password Password del nuovo utente.
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("username") String username,
                       @RequestParam("password") String password) {

        User u = new User(username, password, null);
        ds.userDao().persist(u);
        return;
    }
    
    /**
     * Eccezione che comunica un errore di autenticazione.
     */

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    class AuthFailException extends RuntimeException {}

    /**
     * Metodo che implementa la richiesta GET per ottenere
     * il token di autenticazione di un utente.
     *
     * @param token Concatenazione di email e password dell'utente.
     * @return Restituisce il token di autenticazione per l'utente.
     */

    @RequestMapping(value= "/token", method = RequestMethod.GET)
    public String token(@RequestHeader("X-AuthData") String token) {

        String[] userData = token.split("::");

        User u = ds.userDao().find(userData[0]);

        if (u != null && u.equals(new User(userData[0], userData[1], null)))
            return u.getAuthToken().getToken();

        throw new AuthFailException();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    class PairingFailedException extends RuntimeException {}

    /**
     * Metodo che implementa la richiesta PUT per effettuare
     * il pairing tra backend e applicazione android.
     *
     * @param authToken Token di riconoscimento.
     * @param body Mappa che contiene il token temporaneo di identificazione necessario al pairing in formato JSON.
     */

    @RequestMapping(value= "/pair", method = RequestMethod.PUT)
    public void pair(@RequestHeader("X-AuthToken") String authToken,
                     @RequestBody MultiValueMap<String,String> body) {

        String tempToken = body.getFirst("temp_token");

        AuthToken t = new AuthToken(authToken);
        User u = ds.userDao().find(t.getEmail());
        String deviceId = ds.tempTokenDao().deviceId(tempToken);
        if (deviceId == null)
            throw new PairingFailedException();

        ds.userDao().persist(new User(u.getEmail(), u.getPassword(), ));
    }

    /**
     * Metodo che implementa la richiesta GET per la conferma del pairing
     * da parte dell'applicazione android.
     *
     * @param tempToken Token temporaneo passato dall'applicazione android per la conferma.
     * @return Restituisce il token di autenticazione come conferma della buona riuscita del pairing.
     */

    @RequestMapping(value= "/pair/{temp_token}", method = RequestMethod.GET)
    public String pair(@PathVariable("temp_token") String tempToken) {

        User u = ds.userDao().findDeviceId(ds.tempTokenDao().deviceId(tempToken));
        return u.getAuthToken().getToken();

    }

    /**
     * Metodo che implementa la richiesta PUT per il recupero della
     * password. La buona riuscita di tale chiamata comporta la creazione
     * di una nuova password e la sua comunicazione all'utente tramite
     * un'email.
     *
     * @param body Mappa che contiene l'email dell'utente in formato JSON.
     */

    @RequestMapping(value= "/recovery", method = RequestMethod.PUT)
    public void recovery(@RequestBody MultiValueMap<String,String> body) {

        String email = body.getFirst("email");
        String randomPassword = UUID.randomUUID().toString().substring(0,16);

        Utils.sendMail(email, "Nuova Password", randomPassword);

        ds.userDao().persist(new User(email, randomPassword, null));
    }


}
