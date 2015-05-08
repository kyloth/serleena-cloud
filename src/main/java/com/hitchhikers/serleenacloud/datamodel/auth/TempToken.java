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


package com.kyloth.serleenacloud.datamodel.auth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TempToken {
    private String token;
    private String deviceId;

    public TempToken(String deviceId) {
        this.deviceId = deviceId;
        String s = deviceId + currToken();
        this.token = deviceId + "::" + Util.sha256(s);
    }

    public String getToken() {
        return token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    private static String currToken() {
        return new SimpleDateFormat("yyyyMMddHH").format(new Date());
    }

}
