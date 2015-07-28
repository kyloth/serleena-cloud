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


/**
 * Name: WeatherForecastDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.IWeatherForecastDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;

/**
 * Classe che concretizza IWeatherForecastDao per database MySQL utilizzando JDBC.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class WeatherForecastDao implements IWeatherForecastDao {

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo WeatherForecastDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    WeatherForecastDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Metodo che implementa IWeatherForecast.findAll(Rect, Date, Date).
     *
     * @param region La regione di interesse.
     * @param start Inizio dell'intervallo temporale di interesse.
     * @param end Fine dell'intervallo temporale di interesse.
     * @return Restituisce la lista delle informazioni meteo in base ai parametri specificati.
     */

    public Iterable<WeatherForecast> findAll(Rect region, Date from, Date to) {
        return tpl.query("SELECT MTemperature, MForecast, ATemperature, AForecast, NTemperature, NForecast, Date, NWLongitude, NWLatitude, SELongitude, SELatitude " +
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
                             from, to},
        new RowMapper<WeatherForecast>() {
            @Override
            public WeatherForecast mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new WeatherForecast(rs.getDate("Date"),
                                           new Rect(new Point(rs.getDouble("NWLatitude"),
                                                    rs.getDouble("NWLongitude")),
                                                    new Point(rs.getDouble("SELatitude"),
                                                            rs.getDouble("SELongitude"))),
                                           new WeatherForecast.Forecast(rs.getDouble("MTemperature"), WeatherForecast.WeatherCondition.valueOf(rs.getString("MForecast"))),
                                           new WeatherForecast.Forecast(rs.getDouble("ATemperature"), WeatherForecast.WeatherCondition.valueOf(rs.getString("AForecast"))),
                                           new WeatherForecast.Forecast(rs.getDouble("MTemperature"), WeatherForecast.WeatherCondition.valueOf(rs.getString("MForecast"))));
            }
        });
    }
}
