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

import java.util.Date;

/**
 * Modella un token temporaneo richiesto per il pairing con l'applicativo
 * Android.
 *
 * @use Espone metodi con il quale è possibile costruire un token con validità temporale limitata per un device id, ed ottenere il device id corrispondente ad un dato token.
 * @field token : String Token associato al dispositivo
 * @field deviceId : String Id del dispositivo per il quale viene costruito il token
 * @field date : Date Timestamp per la costruzione del token
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class TempToken {

    /**
     * Token temporaneo costruito secondo lo schema {deviceId}:{sha256(concat(deviceId, currentDate))} dove currentDate è un timestamp del momento attuale.
     */

    private String token;

    /**
     * Id del dispositivo con cui effettuare il pairing.
     */

    private String deviceId;
    
    /**
     * Timestamp per la costruzione del token.
     */

    private Date date;

    /**
     * Costruisce un oggetto TempToken a partire da un deviceId.
     *
     * @param deviceId Id del dispositivo con cui effettuare il pairing.
     */

    public TempToken(String deviceId) {
        this.deviceId = deviceId;
        this.date = new Date((new Date()).getTime() + 900000);
        this.token = Util.sha256(deviceId + date.getTime()).substring(0,6).toUpperCase();
    }

    /**
     * Metodo getter per ottenere il token.
     *
     * @return Restituisce il token temporaneo.
     */

    public String getToken() {
        return token;
    }

    /**
     * Metodo getter per ottenere l'id del dispositivo.
     *
     * @return Restituisce l'id del dispositivo.
     */

    public String getDeviceId() {
        return deviceId;
    }

    public Date getDate() {
        return date;
    }

}
