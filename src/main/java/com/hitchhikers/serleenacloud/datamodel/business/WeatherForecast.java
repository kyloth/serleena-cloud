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
 * @field boundingRect : Rect Oggetto rappresentante l'area geografica relativa alla previsione meteo
 * @field time : Date Data associata alla previsione meteo
 * @field morning : Forecast Previsione associata la mattino
 * @field afternoon : Forecast Previsione associata al pomeriggio
 * @field night : Forecast Previsione associata alla notte
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class WeatherForecast {
    
    /**
     * Enumerazione che rappresenta le diverse condizioni meteo possibili.
     */

    public static enum WeatherCondition {
        SUNNY, CLOUDY, RAINY, STORMY, SNOWY
    }
    
    /**
     * Classe che rappresenta una condizione meteo nei suoi aspetti di
     * temperatura e tempo atmosferico. 
     * @use Viene associata da WeatherForecast a diversi momenti della gioranta (mattina, pomeriggio, notte).
     * @field forecast : WeatherCondition Rappresenta la condizione meteo prevista
     * @field temperature : double Rappresenta la temperatura prevista
     */

    public static class Forecast {

        /**
         * Rappresenta la condizione meteo prevista.
         */

        private WeatherCondition forecast;

        /**
         * Rappresenta la temperatura prevista.
         */

        private double temperature;

        /**
         * Crea una nuova previsione meteo inizializzandone i campi dati.
         */

        public Forecast(double temperature, WeatherCondition forecast) {
            this.temperature = temperature;
            this.forecast = forecast;
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
         * Metodo getter che permette di ottenere le condizioni meteo previste.
         *
         * @return Restituisce un valore WeatherCondition che rappresenta una condizione meteo.
         */

        public WeatherCondition getForecast() {
            return forecast;
        }
    }


    /**
     * Rappresenta l'area geografica associata alla previsione meteo.
     */

    private Rect boundingRect;

    /**
     * Data associata alla previsione meteo.
     */

    private Date time;

    /**
     * Previsione meteo del mattino
     */

    private Forecast morning;

    /**
     * Previsione meteo del pomeriggio
     */

    private Forecast afternoon;

    /**
     * Previsione meteo della notte
     */

    private Forecast night;

    /**
     * Crea una nuova previsione meteo inizializzandone i campi dati.
     */

    public WeatherForecast(Date time, Rect boundingRect, Forecast morning, Forecast afternoon, Forecast night) {
        this.time = time;
        this.boundingRect = boundingRect;
        this.night = night;
        this.afternoon = afternoon;
        this.morning = morning;
    }

    /**
     * Metodo getter che permette di ottenere la previsione meteo per il mattino
     *
     * @return Restituisce un oggetto di tipo Forecast che rappresenta la previsione meteo per il mattino
     */

    public Forecast getMorning() {
        return morning;
    }

    /**
     * Metodo getter che permette di ottenere la previsione meteo per il pomeriggio
     *
     * @return Restituisce un oggetto di tipo Forecast che rappresenta la previsione meteo per il pomeriggio
     */

    public Forecast getAfternoon() {
        return afternoon;
    }

    /**
     * Metodo getter che permette di ottenere la previsione meteo per la notte
     *
     * @return Restituisce un oggetto di tipo Forecast che rappresenta la previsione meteo per la notte
     */

    public Forecast getNight() {
        return night;
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
