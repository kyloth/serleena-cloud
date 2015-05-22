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
 * Name: Util.java
 * Package: com.kyloth.serleenacloud.datamodel.auth
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.auth;

import java.security.MessageDigest;

/**
 * Classe di utilità che fornisce un metodo per calcolare un hash crittografico
 * di una stringa.
 *
 * @use Viene utilizzata da AuthToken e TempToken per il calcolo dell'hash crittografico da fornire come parte del token
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

class Util {

    /**
     * Calcola l'hash crittografico di una stringa di input tramite SHA-256.
     *
     * @param base La stringa da crittografare.
     * @return Restituisce l'hash crittografico della stringa di input.
     */

    static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
