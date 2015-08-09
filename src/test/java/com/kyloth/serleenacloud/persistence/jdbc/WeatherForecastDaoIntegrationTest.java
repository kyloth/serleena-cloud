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
 * Name: WeatherForecastDaoIntegrationTest.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.Iterator;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kyloth.serleenacloud.persistence.jdbc.JDBCDataSource;
import com.kyloth.serleenacloud.persistence.IWeatherForecastDao;

import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.IWeatherForecastDao;

/**
 * Contiene test per la classe WeatherForecastDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class WeatherForecastDaoIntegrationTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static IWeatherForecastDao wfd;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insertWeatherForecast = "INSERT INTO WeatherForecasts (MTemperature, ATemperature, NTemperature, Date, MForecast, AForecast, NForecast, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES (12, 12, 12, '1980-01-01 00:00:01', 'CLOUDY', 'CLOUDY', 'CLOUDY', 6, 15, 15, 6), (13, 13, 13, '1975-01-01 00:00:01', 'SUNNY', 'SUNNY', 'SUNNY', 6, 15, 15, 6), (14, 14, 14, '1980-01-01 00:00:01','SNOWY', 'SNOWY', 'SNOWY', 30, 20, 20, 30);";
        tpl.update(insertWeatherForecast);
        wfd = ds.weatherForecastDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo findAll restituisca tutte le previsioni
     * meteo la cui regione di pertinenza intersechi la regione fornita
     * come parametro e la cui data sia compresa tra le due date fornite
     * come parametri.
     */

    @Test
    public void testFindAll() {
        long timestamp_1 = 284043191000L;
        long timestamp_2 = 347201591000L;
        Rect region = new Rect(new Point(10, 1), new Point(1, 10));
        Iterable<WeatherForecast> forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        Iterator<WeatherForecast> i_forecasts = forecasts.iterator();
        WeatherForecast wf = i_forecasts.next();
        assertFalse(i_forecasts.hasNext());
        assertTrue(wf.getMorning().getForecast() == WeatherForecast.WeatherCondition.CLOUDY);

    }
}
