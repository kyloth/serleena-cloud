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


package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.auth.User;

import com.kyloth.serleenacloud.persistence.*;

import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class JDBCDataSource implements IDataSource {

    private JdbcTemplate tpl;
    private User user;

    private JDBCDataSource(DataSource ds) {
        this.tpl = new JdbcTemplate(ds);
    }

    private JDBCDataSource(JdbcTemplate tpl, User user) {
        this.tpl = tpl;
        this.user = user;
    }

    JdbcTemplate getTpl() {
        return tpl;
    }

    User getUser() {
        return user;
    }

    public IUserDao userDao(){
        return new UserDao(this);
    }

    public IPointOfInterestDao pointOfInterestDao(){
        return new PointOfInterestDao(this);
    }

    public IPathDao pathDao(){
        return new PathDao(this);
    }

    public ILakeDao lakeDao(){
        return new LakeDao(this);
    }

    public IRiverDao riverDao(){
        return new RiverDao(this);
    }

    public IEmergencyContactDao emergencyContactDao(){
        return new EmergencyContactDao(this);
    }

    public ITelemetryDao telemetryDao(){
        return new TelemetryDao(this);
    }

    public IWeatherForecastDao weatherForecastDao(){
        return new WeatherForecastDao(this);
    }

    public ITrackDao trackDao(){
        return new TrackDao(this);
    }

    public IExperienceDao experienceDao(){
        return new ExperienceDao(this);
    }

    public IDataSource forUser(User u){
        return new JDBCDataSource(tpl, u);
    }
}
