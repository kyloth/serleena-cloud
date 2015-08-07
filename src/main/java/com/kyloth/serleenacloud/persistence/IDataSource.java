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
 * Name: IDataSource.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.auth.User;

/**
 * Interfaccia per una classe factory per la creazione di oggetti di tipo DAO.
 *
 * @use Contiene metodi che ritornano le classi DAO disponibili, deve essere inizializzata con l'utente sul quale si vuole agire se il modello lo richiede.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IDataSource {
    
    /**
     * Restituisce una classe DAO di tipo IUserDao.
     *
     * @return Restituisce un oggetto di tipo IUserDao.
     */
    
    public IUserDao userDao();
    
    /**
     * Restituisce una classe DAO di tipo IPointOfInterestDao.
     *
     * @return Restituisce un oggetto di tipo IPointOfInterestDao.
     */

    public IPointOfInterestDao pointOfInterestDao();

    /**
     * Restituisce una classe DAO di tipo IPathDao.
     *
     * @return Restituisce un oggetto di tipo IPathDao.
     */

    public IPathDao pathDao();

    /**
     * Restituisce una classe DAO di tipo IPathDao.
     *
     * @return Restituisce un oggetto di tipo IPathDao.
     */

    public ILakeDao lakeDao();
    
    /**
     * Restituisce una classe DAO di tipo IRiverDao.
     *
     * @return Restituisce un oggetto di tipo IRiverDao.
     */

    public IRiverDao riverDao();

    /**
     * Restituisce una classe DAO di tipo IEmergencyContactDao.
     *
     * @return Restituisce un oggetto di tipo IEmergencyContactDao.
     */

    public IEmergencyContactDao emergencyContactDao();

    /**
     * Restituisce una classe DAO di tipo ITelemetryDao.
     *
     * @return Restituisce un oggetto di tipo ITelemetryDao.
     */

    public ITelemetryDao telemetryDao();

    /**
     * Restituisce una classe DAO di tipo IWeatherForecastDao.
     *
     * @return Restituisce un oggetto di tipo IWeatherForecastDao.
     */

    public IWeatherForecastDao weatherForecastDao();

    /**
     * Restituisce una classe DAO di tipo IElevationRectDao.
     *
     * @return Restituisce un oggetto di tipo IElevationRectDao..
     */

    public IElevationRectDao elevationRectDao();

    /**
     * Restituisce una classe DAO di tipo ITrackDao.
     *
     * @return Restituisce un oggetto di tipo ITrackDao.
     */

    public ITrackDao trackDao();
    
    /**
     * Restituisce una classe DAO di tipo ISyncListDao.
     *
     * @return Restituisce un oggetto di tipo ISyncListDao.
     */

    public ISyncListDao syncListDao();

    /**
     * Restituisce una classe DAO di tipo IExperienceDao.
     *
     * @return Restituisce un oggetto di tipo IExperienceDao.
     */

    public IExperienceDao experienceDao();

    /**
     * Restituisce in IDataSource per uno specifico utente.
     *
     * @param u Utente per il quale si vuole ottenere un IDataSource.
     * @return Restituisce un IDataSource per un particolare utente.
     */

    public IDataSource forUser(User u);
}
