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
 * Date: 2015-05-11
 *
 * History:
 * Version  Programmer       Date        Changes
 * 1.0.0    Gabriele Pozzan  2015-05-11  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.datamodel.sync;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Date;
import com.kyloth.serleenacloud.datamodel.business.IExperience;
import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.IWeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.IEmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.ITrack;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;

/**
 * Contiene i test di unità per la classe SyncOutputData.
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
        Iterable<IExperience> experiences = Arrays.asList(new IExperience[] {
                                                new Experience("Experience_1", null, new ITrack[1], new UserPoint[1], new PointOfInterest[1]),
                                                new Experience("Experience_2", null, new ITrack[1], new UserPoint[1], new PointOfInterest[1])
                                            });
        Iterator<IExperience> i_experiences = experiences.iterator();
        Iterable<IWeatherForecast> forecastData = Arrays.asList(new IWeatherForecast[] {
                    new WeatherForecast(new Date(100), null, 999, null),
                    new WeatherForecast(new Date(200), null, 333, null)
                });
        Iterator<IWeatherForecast> i_forecasts = forecastData.iterator();
        Iterable<IEmergencyContact> emergencyData = Arrays.asList(new IEmergencyContact[] {
                    new EmergencyContact("Contact_1", null, "0"),
                    new EmergencyContact("Contact_2", null, "1")
                });
        Iterator<IEmergencyContact> i_emergencies = emergencyData.iterator();
        SyncOutputData sod = new SyncOutputData(experiences, forecastData, emergencyData);
        Iterable<IExperience> sod_experiences = sod.getExperiences();
        Iterator<IExperience> i_sod_experiences = sod_experiences.iterator();
        Iterable<IWeatherForecast> sod_forecasts = sod.getWeatherData();
        Iterator<IWeatherForecast> i_sod_forecasts = sod_forecasts.iterator();
        Iterable<IEmergencyContact> sod_emergencies = sod.getEmergencyData();
        Iterator<IEmergencyContact> i_sod_emergencies = sod_emergencies.iterator();

        while(i_experiences.hasNext() && i_sod_experiences.hasNext()) {
            IExperience input_experience = i_experiences.next();
            IExperience sod_experience = i_sod_experiences.next();

            assertTrue(input_experience.getName().equals(sod_experience.getName()));
        }
        while(i_forecasts.hasNext() && i_sod_forecasts.hasNext()) {
            IWeatherForecast input_forecast = i_forecasts.next();
            IWeatherForecast sod_forecast = i_sod_forecasts.next();

            assertTrue(input_forecast.getTime().equals(sod_forecast.getTime()));
            assertTrue(input_forecast.getTemperature() == sod_forecast.getTemperature());
        }
        while(i_emergencies.hasNext() && i_sod_emergencies.hasNext()) {
            IEmergencyContact i_emergency = i_emergencies.next();
            IEmergencyContact i_sod_emergency = i_sod_emergencies.next();

            assertTrue(i_emergency.getName().equals(i_sod_emergency.getName()));
        }
    }
}
