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
 * Name: TrackDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.CheckPoint;
import com.kyloth.serleenacloud.persistence.ITrackDao;
import com.kyloth.serleenacloud.persistence.ITelemetryDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza ITrackDao per database MySQL utilizzando JDBC.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class TrackDao implements ITrackDao {

    private JdbcTemplate tpl;
    private ITelemetryDao tDao;

    /**
     * Costruisce un nuovo TrackDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    TrackDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
        tDao = new TelemetryDao(ds);
    }

    /**
     * Metodo che implementa ITrackDao.persist(Track).
     *
     * @param e Il percorso da inserire.
     */

    public void persist(Track track) {
        String trackName = track.getName();
        tpl.update("INSERT INTO Tracks(Name) VALUES(?)", new Object[] {trackName});

        for (Telemetry t : track.getTelemetries())
            tDao.persist(trackName, t);

        for (CheckPoint p : track.getCheckPoints())
            tpl.update("INSERT INTO Checkpoints(TrackName, Longitude, Latitude, Idx) " +
                       "VALUES(?, ?, ?, ?)",
                       new Object[] {trackName, p.getLongitude(), p.getLatitude(), p.getId()});

    }

    /**
     * Metodo che implementa ITrackDao.findAll(String).
     *
     * @param experienceName Nome dell'Esperienza per la quale si vogliono ottenere i percorsi.
     * @return Restituisce la lista dei percorsi relativi all'Esperienza specificata.
     */

    public Iterable<Track> findAll(String experienceName) {
        return tpl.query("SELECT TrackName FROM ExperienceTracks WHERE ExperienceName = ?",
                         new Object[] {experienceName},
        new RowMapper<Track>() {
            @Override
            public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
                return find(rs.getString("TrackName"));
            }
        });
    }

    /**
     * Metodo che implementa ITrackDao.findAll().
     *
     * @return Restituisce la lista dei percorsi.
     */

    public Iterable<Track> findAll() {
        return tpl.query("SELECT TrackName FROM ExperienceTracks",
        new RowMapper<Track>() {
            @Override
            public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
                return find(rs.getString("TrackName"));
            }
        });
    }

    /**
     * Metodo che implementa ITrackDao.find(String).
     *
     * @param name Nome del percorso da ottenere.
     */

    public Track find(String name) {
        Iterable<CheckPoint> checkpoints =
            tpl.query("SELECT Idx, Longitude, Latitude FROM Checkpoints WHERE TrackName = ?",
                      new Object[] {name},
        new RowMapper<CheckPoint>() {
            @Override
            public CheckPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CheckPoint(rs.getDouble("Latitude"),
                                      rs.getDouble("Longitude"),
                                      rs.getInt("Idx"));
            }
        });

        return new Track(name, checkpoints, tDao.findAll(name));

    }
}