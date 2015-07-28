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
 * Name: SyncOutputDataTest.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.sync;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Date;
import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;

/**
 * Contiene test per la classe SyncOutputData.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class SyncOutputDataTest {
    /**
     * Testa la correttezza del costruttore e dei metodi
     * "getter" della classe.
     */
    @Test
    public void testConstructor() {
        Iterable<Experience> experiences = Arrays.asList(new Experience[] {
                                                new Experience("Experience_1", null, new Track[1], new UserPoint[1], new PointOfInterest[1]),
                                                new Experience("Experience_2", null, new Track[1], new UserPoint[1], new PointOfInterest[1])
                                            });
        Iterator<Experience> i_experiences = experiences.iterator();
        Iterable<WeatherForecast> forecastData = Arrays.asList(new WeatherForecast[] {
                new WeatherForecast(new Date(100), null, new WeatherForecast.Forecast(999, null), null, null),
                new WeatherForecast(new Date(200), null, new WeatherForecast.Forecast(333, null), null, null)
                });
        Iterator<WeatherForecast> i_forecasts = forecastData.iterator();
        Iterable<EmergencyContact> emergencyData = Arrays.asList(new EmergencyContact[] {
                    new EmergencyContact("Contact_1", null, "0"),
                    new EmergencyContact("Contact_2", null, "1")
                });
        Iterator<EmergencyContact> i_emergencies = emergencyData.iterator();
        SyncOutputData sod = new SyncOutputData(experiences, forecastData, emergencyData);
        Iterable<Experience> sod_experiences = sod.getExperiences();
        Iterator<Experience> i_sod_experiences = sod_experiences.iterator();
        Iterable<WeatherForecast> sod_forecasts = sod.getWeatherData();
        Iterator<WeatherForecast> i_sod_forecasts = sod_forecasts.iterator();
        Iterable<EmergencyContact> sod_emergencies = sod.getEmergencyData();
        Iterator<EmergencyContact> i_sod_emergencies = sod_emergencies.iterator();

        while(i_experiences.hasNext() && i_sod_experiences.hasNext()) {
            Experience input_experience = i_experiences.next();
            Experience sod_experience = i_sod_experiences.next();

            assertTrue(input_experience.getName().equals(sod_experience.getName()));
        }
        while(i_forecasts.hasNext() && i_sod_forecasts.hasNext()) {
            WeatherForecast input_forecast = i_forecasts.next();
            WeatherForecast sod_forecast = i_sod_forecasts.next();

            assertTrue(input_forecast.getTime().equals(sod_forecast.getTime()));
            assertTrue(input_forecast.getMorning().getTemperature() == sod_forecast.getMorning().getTemperature());
        }
        while(i_emergencies.hasNext() && i_sod_emergencies.hasNext()) {
            EmergencyContact i_emergency = i_emergencies.next();
            EmergencyContact i_sod_emergency = i_sod_emergencies.next();

            assertTrue(i_emergency.getName().equals(i_sod_emergency.getName()));
        }
    }
}
