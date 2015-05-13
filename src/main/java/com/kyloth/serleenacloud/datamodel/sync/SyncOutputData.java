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
 * Name: SyncOutputData.java
 * Package: com.kyloth.serleenacloud.datamodel.sync
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.sync;

import com.kyloth.serleenacloud.datamodel.business.IExperience;
import com.kyloth.serleenacloud.datamodel.business.IWeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.IEmergencyContact;

import java.util.Arrays;

/**
 * Classe che modella i dati di sincronizzazione in output.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class SyncOutputData {

    /**
     * Insieme delle Esperienze da sincronizzare.
     */

    private Iterable<IExperience> experiences;

    /**
     * Insieme dei dati meteo da sincronizzare.
     */

    private Iterable<IWeatherForecast> forecastData;

    /**
     * Insieme dei contatti di emergenza da sincronizzare.
     */

    private Iterable<IEmergencyContact> emergencyData;

    /**
     * Crea un nuovo oggetto SyncOutputData.
     *
     * @param experiences Insieme delle Esperienze da sincronizzare.
     * @param forecastData Insieme dei dati meteo da sincronizzare.
     * @param emergencyData Insieme dei contatti di emergenza da sincronizzare.
     */

    public SyncOutputData(Iterable<IExperience> experiences, Iterable<IWeatherForecast> forecastData, Iterable<IEmergencyContact> emergencyData) {
        this.experiences = experiences;
        this.forecastData = forecastData;
        this.emergencyData = emergencyData;
    }

    /**
     * Crea un nuovo oggetto SyncOutputData.
     *
     * @param experiences Array delle Esperienze da sincronizzare.
     * @param forecastData Array dei dati meteo da sincronizzare.
     * @param emergencyData Array dei contatti di emergenza da sincronizzare.
     */

    public SyncOutputData(IExperience[] experiences, IWeatherForecast[] forecastData, IEmergencyContact[] emergencyData) {
        this.experiences = Arrays.asList(experiences);
        this.forecastData = Arrays.asList(forecastData);
        this.emergencyData = Arrays.asList(emergencyData);
    }

    /**
     * Metodo "getter" per ottenere l'insieme delle Esperienze da sincronizzare.
     *
     * @return Restituisce l'insieme delle Esperienze da sincronizzare.
     */

    public Iterable<IExperience> getExperiences() {
        return experiences;
    }

    /**
     * Metodo "getter" per ottenere l'insieme dei dati meteo da sincronizzare.
     *
     * @return Restituisce l'insieme dei dati meteo da sincronizzare.
     */

    public Iterable<IWeatherForecast> getWeatherData() {
        return forecastData;
    }

    /**
     * Metodo "getter" per ottenere l'insieme dei contatti di emergenza da sincronizzare.
     *
     * @return Restituisce l'insieme dei contatti di emergenza da sincronizzare.
     */

    public Iterable<IEmergencyContact> getEmergencyData() {
        return emergencyData;
    }

}
