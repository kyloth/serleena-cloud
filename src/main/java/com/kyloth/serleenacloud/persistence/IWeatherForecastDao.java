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
 * Name: IWeatherForecastDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;

import java.util.Date;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo WeatherForecast.
 *
 * @use Contiene metodi per cercare informazioni meteo nella base di dati.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IWeatherForecastDao {
    
    /**
     * Permette di trovare tutte le informazioni meteo relative a una
     * certa regione, per un certo intervallo di tempo.
     *
     * @param region La regione di interesse.
     * @param start Inizio dell'intervallo temporale di interesse.
     * @param end Fine dell'intervallo temporale di interesse.
     * @return Restituisce la lista delle informazioni meteo in base ai parametri specificati.
     */

    Iterable<WeatherForecast> findAll(Rect region, Date start, Date end);
}
