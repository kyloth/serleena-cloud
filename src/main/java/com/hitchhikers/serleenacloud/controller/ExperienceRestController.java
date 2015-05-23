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

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.auth.AuthToken;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;

import java.util.ArrayList;

@RestController
@RequestMapping("/experiences")
public class ExperienceRestController {

    static IDataSource ds = DataSourceFactory.getDataSource();
    static ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(method = RequestMethod.GET)
    public Iterable<String> list(@RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        ArrayList<String> experiences = new ArrayList<String>();
        for(Experience e : dataSource.experienceDao().findAll())
            experiences.add(e.getName());

        return experiences;
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public Experience get(@PathVariable("id") String id,
                          @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        return dataSource.experienceDao().find(id);
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id,
                       @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        dataSource.experienceDao().delete(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("name") String name,
                       @RequestParam("pois") String pois,
                       @RequestParam("ups") String ups,
                       @RequestParam("tracks") String tracks,
                       @RequestParam("from") String from,
                       @RequestParam("to") String to,
                       @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);
        try {

            PointOfInterest[] _pois = mapper.readValue(pois, PointOfInterest[].class);
            UserPoint[] _ups = mapper.readValue(ups, UserPoint[].class);
            Track[] _tracks = mapper.readValue(tracks, Track[].class);
            Point _from = mapper.readValue(from, Point.class);
            Point _to = mapper.readValue(to, Point.class);

            Experience experience = new Experience(name, new Rect(_from, _to), _tracks, _ups, _pois);
            dataSource.experienceDao().persist(experience);
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public void update(@RequestBody MultiValueMap<String,String> body,
                       @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        try {

            String name = mapper.readValue(body.getFirst("name"), String.class);
            PointOfInterest[] pois = mapper.readValue(body.getFirst("pois"), PointOfInterest[].class);
            UserPoint[] ups = mapper.readValue(body.getFirst("ups"), UserPoint[].class);
            Track[] tracks = mapper.readValue(body.getFirst("tracks"), Track[].class);
            Point from = mapper.readValue(body.getFirst("from"), Point.class);
            Point to = mapper.readValue(body.getFirst("to"), Point.class);

            Experience experience = new Experience(name, new Rect(from, to), tracks, ups, pois);
            dataSource.experienceDao().persist(experience);

        } catch (IOException e) {}
    }


    @RequestMapping(value= "/{id}/tracks/{track_id}", method = RequestMethod.GET)
    public Track get(@PathVariable("id") String id,
                     @PathVariable("track_id") String track_id,
                     @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        for(Track t : dataSource.trackDao().findAll(id))
            if (t.getName().equals(track_id))
                return t;

        return null;
    }

}
