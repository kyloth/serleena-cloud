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

public interface IWeatherForecast {

    public static enum WeatherCondition {
        SUNNY, CLOUDY, RAINY, STORMY, SNOWY
    }

    public WeatherCondition getForecast();
    public double getTemperature();
    public IRect getBoundingRect();
    public Date getTime();

}
