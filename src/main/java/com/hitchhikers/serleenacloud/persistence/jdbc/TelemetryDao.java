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

import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.TelemetryEvent;
import com.kyloth.serleenacloud.persistence.ITelemetryDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TelemetryDao implements ITelemetryDao {

    private JdbcTemplate tpl;

    TelemetryDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public void persist(final String trackName, Telemetry t) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        tpl.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Telemetries(TrackName) VALUES (?)",
                                       new String[] {"Id"});
                ps.setString(1, trackName);
                return ps;
            }
        },
        keyHolder);
        for (TelemetryEvent event : t.getEvents()) {
            tpl.update("INSERT INTO TelemetryEvents(TelemetryId, Value, Type, Date) " +
                       "VALUES(?, ?, ?, ?)",
                       new Object[] { keyHolder.getKey(),
                                      event.getValue(),
                                      event.eventType().toString(),
                                      event.getTime()
                                    });
        }
    }

    public Iterable<Telemetry> findAll(final String trackName) {
        return tpl.query("SELECT Id FROM Telemetries WHERE TrackName = ?",
                         new Object[] {trackName},
                         new RowMapper<Telemetry>() {
                             @Override
                             public Telemetry mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 String id = rs.getString("Id");
                                 Iterable<TelemetryEvent> events =
                                     tpl.query("SELECT Value, Type, Date " +
                                               "FROM TelemetryEvents " +
                                               "WHERE TelemetryId = ?",
                                               new Object[] {id},
                                               new RowMapper<TelemetryEvent>() {
                                                   @Override
                                                   public TelemetryEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
                                                       return new TelemetryEvent(TelemetryEvent.EventType.valueOf(rs.getString("Type")),
                                                                                 rs.getDate("Date"),
                                                                                 rs.getDouble("Value"));
                                                   }
                                               });
                                 return new Telemetry(events, trackName, id);
                             }
                         });
    }
}
