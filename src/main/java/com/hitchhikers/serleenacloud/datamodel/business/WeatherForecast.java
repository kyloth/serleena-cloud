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


package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.IRect;

import java.util.Date;

public class WeatherForecast implements IWeatherForecast {

    private WeatherCondition forecast;
    private IRect boundingRect;
    private double temperature;
    private Date time;

    public WeatherForecast(Date time, IRect boundingRect, double temperature, WeatherCondition forecast) {
        this.time = time;
        this.boundingRect = boundingRect;
        this.temperature = temperature;
        this.forecast = forecast;
    }

    public WeatherCondition getForecast() {
        return forecast;
    }

    public double getTemperature() {
        return temperature;
    }

    public IRect getBoundingRect() {
        return boundingRect;
    }

    public Date getTime() {
        return time;
    }
}
