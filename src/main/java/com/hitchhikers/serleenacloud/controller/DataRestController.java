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

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.sync.SyncOutputData;
import com.kyloth.serleenacloud.datamodel.sync.SyncInputData;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.auth.AuthToken;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;

@RestController
@RequestMapping("/data")
public class DataRestController {

    static IDataSource ds = DataSourceFactory.getDataSource();
    static ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public SyncOutputData get(@RequestHeader("X-AuthToken") String authToken) {

        AuthToken t = new AuthToken(authToken);
        User u = ds.userDao().find(t.getEmail());

        Iterable<Experience> es = ds.forUser(u).syncListDao().findAll();
        ArrayList<EmergencyContact> ec = new ArrayList<EmergencyContact>();
        ArrayList<WeatherForecast> wf = new ArrayList<WeatherForecast>();

        for (Experience e : es) {
            Rect r = e.getBoundingRect();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 5);

            for (WeatherForecast forecast : ds.weatherForecastDao().findAll(r, new Date(), cal.getTime()))
                wf.add(forecast);

            for (EmergencyContact contact : ds.emergencyContactDao().findAll(r))
                ec.add(contact);
        }

        return new SyncOutputData(es, wf, ec);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void put(@RequestParam("data") String id,
                    @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);
        try {
            for (SyncInputData input : mapper.readValue(id, SyncInputData[].class)) {

                Experience e = dataSource.experienceDao().find(input.getExperienceName());
                ArrayList<UserPoint> userPoints = new ArrayList<UserPoint>();

                for (UserPoint u : input.getUserPoints())
                    userPoints.add(u);
                for (UserPoint u : e.getUserPoints())
                    userPoints.add(u);

                Experience newE = new Experience(e.getName(), e.getBoundingRect(), e.getTracks(), userPoints, e.getPOIs());
                dataSource.experienceDao().persist(newE);

                for (Telemetry t : input.getTelemetryData())
                    dataSource.telemetryDao().persist(t.getTrack(), t);
            }
        } catch (IOException e) {}
    }


    @RequestMapping(value= "/sync", method = RequestMethod.GET)
    public Iterable<String> getSync(@RequestHeader("X-AuthToken") String authToken) {
        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());

        IDataSource dataSource = ds.forUser(user);

        ArrayList<String> syncList = new ArrayList<String>();
        for(Experience e : dataSource.syncListDao().findAll())
            syncList.add(e.getName());

        return syncList;
    }

    @RequestMapping(value= "/sync", method = RequestMethod.PUT)
    public void putSync(@RequestBody MultiValueMap<String,String> body,
                        @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        String lists = body.getFirst("exp_list");

        try {
            String[] experiences = mapper.readValue(lists, String[].class);
            ArrayList<Experience> syncList = new ArrayList<Experience>();

            for (String e : experiences)
                syncList.add(dataSource.experienceDao().find(e));

            dataSource.syncListDao().persist(syncList);
        } catch (IOException e) {}
    }
}
