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


package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.ITrack;
import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.business.ITelemetry;
import com.kyloth.serleenacloud.datamodel.business.CheckPoint;
import com.kyloth.serleenacloud.persistence.ITrackDao;
import com.kyloth.serleenacloud.persistence.ITelemetryDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDao implements ITrackDao {

    private JdbcTemplate tpl;
    private ITelemetryDao tDao;

    TrackDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public void persist(ITrack track) {
        String trackName = track.getName();
        tpl.update("INSERT INTO Tracks(Name) VALUES(?)", new Object[] {trackName});

        for (ITelemetry t : track.getTelemetries())
            tDao.persist(trackName, t);

        for (CheckPoint p : track.getCheckPoints())
            tpl.update("INSERT INTO Checkpoints(TrackName, Longitude, Latitude, Idx) " +
                       "VALUES(?, ?, ?, ?)",
                       new Object[] {trackName, p.getLongitude(), p.getLatitude(), p.getId()});

    }

    public Iterable<ITrack> findAll(String experienceName) {
        return tpl.query("SELECT TrackName FROM ExperienceTracks WHERE ExperienceName = ?",
                         new Object[] {experienceName},
                         new RowMapper<ITrack>() {
                             @Override
                             public ITrack mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 return find(rs.getString("TrackName"));
                             }
                         });
    }

    public Iterable<ITrack> findAll() {
        return tpl.query("SELECT TrackName FROM ExperienceTracks",
                         new RowMapper<ITrack>() {
                             @Override
                             public ITrack mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 return find(rs.getString("TrackName"));
                             }
                         });
    }

    public ITrack find(String name) {
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
