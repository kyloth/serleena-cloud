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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.business.IExperience;
import com.kyloth.serleenacloud.datamodel.business.ITelemetry;
import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.IEmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.IWeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.sync.SyncOutputData;
import com.kyloth.serleenacloud.datamodel.sync.SyncInputData;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.auth.AuthToken;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;

@RestController
@RequestMapping("/data")
public class DataRestController {

    static IDataSource ds = DataSourceFactory.getDataSource();

    @RequestMapping(method = RequestMethod.GET)
    public SyncOutputData get(@RequestHeader("X-AuthToken") String authToken) {

        AuthToken t = new AuthToken(authToken);
        User u = ds.userDao().find(t.getEmail());

        Iterable<IExperience> es = ds.forUser(u).syncListDao().findAll();
        ArrayList<IEmergencyContact> ec = new ArrayList<IEmergencyContact>();
        ArrayList<IWeatherForecast> wf = new ArrayList<IWeatherForecast>();

        for (IExperience e : es) {
            IRect r = e.getBoundingRect();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 5);

            for (IWeatherForecast forecast : ds.weatherForecastDao().findAll(r, new Date(), cal.getTime()))
                wf.add(forecast);

            for (IEmergencyContact contact : ds.emergencyContactDao().findAll(r))
                ec.add(contact);
        }

        return new SyncOutputData(es, wf, ec);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void put(@RequestParam("data") Iterable<SyncInputData> id,
                    @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        for (SyncInputData input : id) {

            IExperience e = dataSource.experienceDao().find(input.getExperienceName());
            ArrayList<UserPoint> userPoints = new ArrayList<UserPoint>();

            for (UserPoint u : input.getUserPoints())
                userPoints.add(u);
            for (UserPoint u : e.getUserPoints())
                userPoints.add(u);

            IExperience newE = new Experience(e.getName(), e.getBoundingRect(), e.getTracks(), userPoints, e.getPOIs());
            dataSource.experienceDao().persist(newE);

            for (ITelemetry t : input.getTelemetryData())
                dataSource.telemetryDao().persist(t.getTrack(), t);
        }
    }

}
