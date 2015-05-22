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
 * Name: AuthToken.java
 * Package: com.kyloth.serleenacloud.datamodel.auth
 * Author: Nicola Mometto
 * Date: 2015-05-05
 *
 * History:
 * Version  Programmer          Date        Changes
 * 1.0.0    Nicola Mometto      2015-05-05  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.auth;

/**
 * Modella un token di autenticazione richiesto da alcune route
 *
 * @use Espone metodi con i quali è possibile verificare se il token autentica un utente, utilizzato dalla classe AbstractAuthenticationProcessingFilter
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class AuthToken {

    /**
     * Indirizzo email dell'utente.
     */

    private String email;

    /**
     * Token strutturato secondo lo schema {email}:{sha256(concat(email, password))}.
     */

    private String token;

    /**
     * Crea un nuovo oggetto AuthToken a partire da un utente.
     *
     * @param user L'utente per il quale viene generato il token di autenticazione.
     */

    AuthToken(User user) {
        this.email = user.getEmail();
        String s = email + user.getPassword();
        this.token = email + "::" + Util.sha256(s);
    }

    /**
     * Crea un nuovo oggetto AuthToken a partire da un token.
     *
     * @param token Il token fornito per la costruzione.
     */

    public AuthToken(String token) {
        this.email = token.split("::")[0];
        this.token = token;
    }

    /**
     * Metodo "getter" per ottenere il token.
     *
     * @return Il token di autenticazione.
     */

    public String getToken() {
        return token;
    }

    /**
     * Verifica che un AuthToken sia valido per un dato User.
     *
     * @param u User per il quale si vuole valutare la validità del token.
     * @return True se l'AuthToken è valido per l'utente, False altrimenti.
     */

    public boolean validFor(User u) {
        return u.getAuthToken().getToken().equals(getToken());
    }


    /**
     * Metodo "getter" per ottenere l'email.
     *
     * @return L'email associata al token..
     */

    public String getEmail() {
        return email;
    }

}
