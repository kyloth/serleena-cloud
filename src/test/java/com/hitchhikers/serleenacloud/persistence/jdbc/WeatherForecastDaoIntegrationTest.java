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
        String insertWeatherForecast1 = "INSERT INTO WeatherForecasts (MTemperature, ATemperature, NTemperature, Date, MForecast, AForecast, NForecast, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES (50, 50, 50, '1980-01-01 00:00:01', 'RAINY', 'RAINY', 'RAINY', 11.646284, 45.277693, 11.650876, 45.274461);";
        tpl.update(insertWeatherForecast);
        tpl.update(insertWeatherForecast1);
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
        assertTrue(wf.getMorning().getForecast() == WeatherForecast.WeatherCondition.CLOUDY);
    }

    @Test
    public void testFindAllRealDataHitRegion() {
        long timestamp_1 = 284043191000L;
        long timestamp_2 = 347201591000L;
        //Punto NW della regione cade all'interno della regione delle previsioni
        Rect region = new Rect(new Point(45.275700, 11.649052), new Point(45.273540, 11.656369));
        Iterable<WeatherForecast> forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        Iterator<WeatherForecast> i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Punto SE della regione cade all'interno della regione delle previsioni
        region = new Rect(new Point(45.279912, 11.643151), new Point(45.275745, 11.649224));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Punto NE della regione cade all'interno della regione delle previsioni
        region = new Rect(new Point(45.275866, 11.639632), new Point(45.269483, 11.649230));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Punto SW della regione cade all'interno della regione delle previsioni
        region = new Rect(new Point(45.281281, 11.648720), new Point(45.275427, 11.659335));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Regione completamente contenuta in quella delle previsioni
        region = new Rect(new Point(45.275876, 11.648643), new Point(45.275230, 11.649536));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Punto NW della regione delle previsioni cade all'interno della regione
        region = new Rect(new Point(45.278123, 11.645123), new Point(45.276123, 11.647123));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Punto SE della regione delle previsioni cade all'interno della regione
        region = new Rect(new Point(45.277512, 11.647123), new Point(45.265123, 11.660123));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Punto NE della regione delle previsioni cade all'interno della regione
        region = new Rect(new Point(45.275123, 11.630123), new Point(45.265123, 11.648123));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Punto SW della regione delle previsioni cade all'interno della regione
        region = new Rect(new Point(45.279123, 11.649876), new Point(45.276834, 11.651647));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
        //Regione delle previsioni completamente contenuta nella regione
        region = new Rect(new Point(45.278656, 11.639536), new Point(45.273758, 11.650991));
        forecasts = wfd.findAll(region, new Date(timestamp_1), new Date(timestamp_2));
        i_forecasts = forecasts.iterator();
        assertTrue(i_forecasts.next().getMorning().getForecast() == WeatherForecast.WeatherCondition.RAINY);
        assertFalse(i_forecasts.hasNext());
    }
}
