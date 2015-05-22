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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;
import com.kyloth.serleenacloud.datamodel.auth.TempToken;

@RestController
@RequestMapping("/token")
public class TempTokenRestController {

    static IDataSource ds = DataSourceFactory.getDataSource();

    @RequestMapping(value= "/{kyloth_id}", method = RequestMethod.GET)
    public String token(@PathVariable("kyloth_id") String id) {
        return (new TempToken(id)).getToken();
    }

}
