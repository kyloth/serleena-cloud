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
 * Name: TempToken.java
 * Package: com.kyloth.serleenacloud.datamodel.auth
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.auth;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Modella un token temporaneo richiesto per il pairing con l'applicativo
 * Android.
 *
 * @use Espone metodi con il quale è possibile costruire un token con validità temporale limitata per un device id, ed ottenere il device id corrispondente ad un dato token
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class TempToken {

    /**
     * Token temporaneo costruito secondo lo schema {deviceId}:{sha256(concat(deviceId, currentDate))} dove "currentDate" è un timestamp del momento attuale.
     */

    private String token;

    /**
     * Id del dispositivo con cui effettuare il pairing.
     */

    private String deviceId;

    /**
     * Costruisce un oggetto TempToken a partire da un deviceId.
     *
     * @param deviceId Id del dispositivo con cui effettuare il pairing.
     */

    public TempToken(String deviceId) {
        this(deviceId, new Date());
    }

    /**
     * Costruisce un TempToken a partire da un deviceId e da un particolare
     * timestamp.
     *
     * @param deviceId Id del dispositivo con cui effettuare il pairing.
     * @param currDate Timestamp fornito per calcolare il token.
     */

    TempToken(String deviceId, Date currDate) {
        this.deviceId = deviceId;
        String s = deviceId + currToken(currDate);
        this.token = deviceId + "::" + Util.sha256(s);
    }

    /**
     * Metodo "getter" per ottenere il token.
     *
     * @return Restituisce il token temporaneo.
     */

    public String getToken() {
        return token;
    }

    /**
     * Metodo "getter" per ottenere l'id del dispositivo.
     *
     * @return Restituisce l'id del dispositivo.
     */

    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Restituisce il timestamp fornito secondo lo schema
     * {anno}{mese}{giorno}{ora}.
     *
     * @param date Timestamp da formattare.
     * @return Il timestamp formattato.
     */

    private static String currToken(Date date) {
        return new SimpleDateFormat("yyyyMMddHH").format(date);
    }

}
