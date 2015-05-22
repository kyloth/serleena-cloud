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

import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.IWeatherForecast;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.IWeatherForecastDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;

public class WeatherForecastDao implements IWeatherForecastDao {

    private JdbcTemplate tpl;

    WeatherForecastDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public Iterable<IWeatherForecast> findAll(IRect region, Date from, Date to) {
        return tpl.query("SELECT Temperature, Date, Forecast, NWLongitude, NWLatitude, SELongitude, SELatitude " +
                         "FROM WeatherForecasts " +
                         "WHERE (((NWLatitude BETWEEN ? AND ?) AND (NWLongitude BETWEEN ? AND ?)) " +
                         "OR ((SELatitude BETWEEN ? AND ? ) AND (SELongitude BETWEEN ? AND ? ))" +
                         "OR ((NWLongitude BETWEEN ? AND ?) AND (SELatitude BETWEEN ? AND ?)) " +
                         "OR ((NWLatitude BETWEEN ? AND ?) AND (SELongitude BETWEEN ? AND ?))) " +
                         "AND (Date BETWEEN ? AND ?)",
                         new Object[] {
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             from,
                             to
                         },
        new RowMapper<IWeatherForecast>() {
            @Override
            public IWeatherForecast mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new WeatherForecast(rs.getDate("Date"),
                                           new Rect(new Point(rs.getDouble("NWLatitude"),
                                                    rs.getDouble("NWLongitude")),
                                                    new Point(rs.getDouble("SELatitude"),
                                                            rs.getDouble("SELongitude"))),
                                           rs.getDouble("Temperature"),
                                           IWeatherForecast.WeatherCondition.valueOf(rs.getString("Forecast")));
            }
        });
    }
}
