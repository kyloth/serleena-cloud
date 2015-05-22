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
import com.kyloth.serleenacloud.datamodel.auth.TempToken;

@RestController
@RequestMapping("/users")
public class UserRestController {

    static IDataSource ds = DataSourceFactory.getDataSource();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("username") String username,
                       @RequestParam("password") String password) {

        User u = new User(username, password, null);
        ds.userDao().persist(u);
        return;
    }

    @RequestMapping(value= "/token", method = RequestMethod.GET)
    public String token(@RequestHeader("X-AuthData") String token) {

        String[] userData = token.split("::");

        User u = new User(userData[0], userData[1], null);
        if (ds.userDao().find(userData[0]).equals(u))
            return u.getAuthToken().getToken();

        return null;
    }

    @RequestMapping(value= "/pair", method = RequestMethod.PUT)
    public void pair(@RequestHeader("X-AuthToken") String authToken,
                     @RequestBody MultiValueMap<String,String> body) {

        String tempToken = body.getFirst("temp_token");

        AuthToken t = new AuthToken(authToken);
        User u = ds.userDao().find(t.getEmail());

        ds.userDao().persist(new User(u.getEmail(), u.getPassword(), tempToken.split("::")[0]));
    }

    @RequestMapping(value= "/pair/{temp_token}", method = RequestMethod.GET)
    public String pair(@PathVariable("temp_token") String tempToken) {

        User u = ds.userDao().find(tempToken.split("::")[0]);
        return u.getAuthToken().getToken();

    }

}
