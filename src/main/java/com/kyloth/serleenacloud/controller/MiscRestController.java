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

import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.business.Path;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

@RestController
public class MiscRestController {

    static IDataSource ds = DataSourceFactory.getDataSource();

    static void setDataSource(IDataSource ds) {
        MiscRestController.ds = ds;
    }

    @RequestMapping(value= "/token/{kyloth_id}", method = RequestMethod.GET)
    public String token(@PathVariable("kyloth_id") String id) {
        return (new TempToken(id)).getToken();
    }

    @RequestMapping(value= "/poi/{from}/{to}", method = RequestMethod.GET)
    public Iterable<PointOfInterest> poi(@PathVariable("from") String from,
                                         @PathVariable("to") String to) {
        String[] _from = from.split(";");
        String[] _to = to.split(";");

        Rect r = new Rect(new Point(Double.valueOf(_from[0]),
                                    Double.valueOf(_from[1])),
                          new Point(Double.valueOf(_to[0]),
                                    Double.valueOf(_to[1])));

        return ds.pointOfInterestDao().findAll(r);
    }

    @RequestMapping(value= "/paths/{from}/{to}", method = RequestMethod.GET)
    public Iterable<Path> paths(@PathVariable("from") String from,
                                @PathVariable("to") String to) {
        String[] _from = from.split(";");
        String[] _to = to.split(";");

        Rect r = new Rect(new Point(Double.valueOf(_from[0]),
                                    Double.valueOf(_from[1])),
                          new Point(Double.valueOf(_to[0]),
                                    Double.valueOf(_to[1])));

        return ds.pathDao().findAll(r);
    }

}
