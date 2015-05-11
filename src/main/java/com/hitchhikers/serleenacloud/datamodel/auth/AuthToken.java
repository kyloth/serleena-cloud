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

public class AuthToken {

    private String email;
    private String token;

    AuthToken(User user) {
        this.email = user.getEmail();
        String s = email + user.getPassword();
        this.token = email + "::" + Util.sha256(s);
    }

    public AuthToken(String token) {
        this.email = token.split("::")[0];
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public boolean validFor(User u) {
        return u.getAuthToken().getToken().equals(getToken());
    }
}
