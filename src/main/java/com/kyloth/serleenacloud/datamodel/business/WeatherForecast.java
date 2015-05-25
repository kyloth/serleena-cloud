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
 * Name: WeatherForecast.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.Rect;

import java.util.Date;

/**
 * Classe che rappresenta informazioni riguardanti il meteo e la
 * temperatura di un'area di mappa in un certo periodo di tempo.
 *
 * @use Viene utilizzata da DataRestController per creare JSON richiesto dall'applicazione android
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class WeatherForecast {

    public static enum WeatherCondition {
        SUNNY, CLOUDY, RAINY, STORMY, SNOWY
    }

    /**
     * Rappresenta la condizione meteo prevista.
     */

    private WeatherCondition forecast;

    /**
     * Rappresenta l'area geografica associata alla previsione meteo.
     */

    private Rect boundingRect;

    /**
     * Rappresenta la temperatura prevista.
     */

    private double temperature;

    /**
     * Data associata alla previsione meteo.
     */

    private Date time;

    /**
     * Crea una nuova previsione meteo inizializzandone i campi dati.
     */

    public WeatherForecast(Date time, Rect boundingRect, double temperature, WeatherCondition forecast) {
        this.time = time;
        this.boundingRect = boundingRect;
        this.temperature = temperature;
        this.forecast = forecast;
    }

    /**
     * Metodo getter che permette di ottenere le condizioni meteo previste.
     *
     * @return Restituisce un valore WeatherCondition che rappresenta una condizione meteo.
     */

    public WeatherCondition getForecast() {
        return forecast;
    }

    /**
     * Metodo getter che permette di ottenere la temperatura prevista.
     *
     * @return Restituisce un valore double che rappresenta la temperatura prevista.
     */

    public double getTemperature() {
        return temperature;
    }

    /**
     * Metodo getter che permette di ottenere l'area geografica relativa alle previsioni.
     *
     * @return Restituisce un oggetto di tipo Rect che rappresenta l'area geografica relativa alle previsioni meteo.
     */

    public Rect getBoundingRect() {
        return boundingRect;
    }

    /**
     * Metodo getter che permette di ottenere la data alla quale le previsioni sono relative.
     *
     * @return Restituisce un oggetto Date che rappresenta la data alla quale le previsioni sono relative.
     */

    public Date getTime() {
        return time;
    }
}
