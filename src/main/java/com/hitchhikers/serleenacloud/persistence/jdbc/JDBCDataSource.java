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
 * Name: JDBCDataSource.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.auth.User;

import com.kyloth.serleenacloud.persistence.*;

import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * Classe che concretizza IDataSource per database MySQL utilizzando JDBC.
 *
 * @field tpl : JdbcTemplate Template JDBC per la connessione alla base di dati
 * @field user : User Oggetto rappresentate l'utente i quali dati si vogliono manipolare
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class JDBCDataSource implements IDataSource {

    /**
     * Template JDBC per la connessione alla base di dati.
     */

    private JdbcTemplate tpl;
    
    /**
     * Utente i quali dati si vogliono manipolare.
     */

    private User user;
    
    /**
     * Costruisce un nuovo JDBCDataSource.
     *
     * @param ds DataSource per la connessione al database.
     */

    private JDBCDataSource(DataSource ds) {
        this.tpl = new JdbcTemplate(ds);
    }
    
    /**
     * Costruisce un nuovo JDBCDataSource per un particolare utente.
     *
     * @param tpl JdbcTemplate per la connessione al database.
     * @param tpl user Utente per il quale si vuole creare il data source.
     */

    private JDBCDataSource(JdbcTemplate tpl, User user) {
        this.tpl = tpl;
        this.user = user;
    }
    
    /**
     * Permette di ottenere il JdbcTemplate per la connessione al database.
     *
     * @return Restituisce un oggetto JdbcTemplate.
     */

    JdbcTemplate getTpl() {
        return tpl;
    }

    /**
     * Permette di ottenere l'utente, se presente, cui il data source è relativo.
     *
     * @return Restuisce il campo user della classe.
     */

    User getUser() {
        return user;
    }

    /**
     * Implementa IDataSource.userDao().
     *
     * @return Restituisce un IUserDao.
     */

    public IUserDao userDao() {
        return new UserDao(this);
    }

    /**
     * Implementa IDataSource.pointOfInterestDao().
     *
     * @return Restituisce un IPointOfInterestDao.
     */

    public IPointOfInterestDao pointOfInterestDao() {
        return new PointOfInterestDao(this);
    }
    
    /**
     * Implementa IDataSource.pathDao().
     *
     * @return Restituisce un IPathDao.
     */

    public IPathDao pathDao() {
        return new PathDao(this);
    }
    
    /**
     * Implementa IDataSource.lakeDao().
     *
     * @return Restituisce un ILakeDao.
     */

    public ILakeDao lakeDao() {
        return new LakeDao(this);
    }
    
    /**
     * Implementa IDataSource.riverDao().
     *
     * @return Restituisce un IRiverDao.
     */

    public IRiverDao riverDao() {
        return new RiverDao(this);
    }
    
    /**
     * Implementa IDataSource.emergencyContactDao().
     *
     * @return Restituisce un IEmergencyContactDao.
     */

    public IEmergencyContactDao emergencyContactDao() {
        return new EmergencyContactDao(this);
    }
    
    /**
     * Implementa IDataSource.telemetryDao().
     *
     * @return Restituisce un ITelemetryDao.
     */

    public ITelemetryDao telemetryDao() {
        return new TelemetryDao(this);
    }
    
    /**
     * Implementa IDataSource.weatherForecastDao().
     *
     * @return Restituisce un IWeatherForecastDao.
     */

    public IWeatherForecastDao weatherForecastDao() {
        return new WeatherForecastDao(this);
    }

    /**
     * Implementa IDataSource.elevationRectDao().
     *
     * @return Restituisce un IElevationRectDao.
     */

    public IElevationRectDao elevationRectDao() {
        return new ElevationRectDao(this);
    }

    /**
     * Implementa IDataSource.trackDao().
     *
     * @return Restituisce un ITrackDao.
     */

    public ITrackDao trackDao() {
        return new TrackDao(this);
    }
    
    /**
     * Implementa IDataSource.experienceDao().
     *
     * @return Restituisce un IExperienceDao.
     */

    public IExperienceDao experienceDao() {
        return new ExperienceDao(this);
    }

    /**
     * Implementa IDataSource.tempTokenDao().
     *
     * @return Restituisce un ITempTokenDao.
     */

    public ITempTokenDao tempTokenDao() {
        return new TempTokenDao(this);
    }

    /**
     * Implementa IDataSource.syncListDao().
     *
     * @return Restituisce un ISyncListDao.
     */

    public ISyncListDao syncListDao() {
        return new SyncListDao(this);
    }
    
    /**
     * Implementa IDataSource.forUser(User).
     *
     * @param u Utente per il quale si vuole ottenere un JDBCDataSource.
     * @return Restituisce un JDBCDataSource configurato per un particolare utente.
     */

    public IDataSource forUser(User u) {
        return new JDBCDataSource(tpl, u);
    }
}
