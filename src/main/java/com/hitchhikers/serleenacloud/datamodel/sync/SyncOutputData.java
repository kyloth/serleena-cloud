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


package com.kyloth.serleenacloud.datamodel.sync;

import com.kyloth.serleenacloud.datamodel.business.IExperience;
import com.kyloth.serleenacloud.datamodel.business.IWeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.IEmergencyContact;

import java.util.Arrays;

public class SyncOutputData {
    private Iterable<IExperience> experiences;
    private Iterable<IWeatherForecast> forecastData;
    private Iterable<IEmergencyContact> emergencyData;

    public SyncOutputData(Iterable<IExperience> experiences, Iterable<IWeatherForecast> forecastData, Iterable<IEmergencyContact> emergencyData) {
        this.experiences = experiences;
        this.forecastData = forecastData;
        this.emergencyData = emergencyData;
    }

    public SyncOutputData(IExperience[] experiences, IWeatherForecast[] forecastData, IEmergencyContact[] emergencyData) {
        this.experiences = Arrays.asList(experiences);
        this.forecastData = Arrays.asList(forecastData);
        this.emergencyData = Arrays.asList(emergencyData);
    }

    public Iterable<IExperience> getExperiences() {
        return experiences;
    }

    private Iterable<IWeatherForecast> getWeatherData() {
        return forecastData;
    }

    private Iterable<IEmergencyContact> getEmergencyData() {
        return emergencyData;
    }

}
