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
 * Name: TelemetryDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.Telemetry;
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

/**
 * Classe che concretizza ITelemetryDao per database MySQL utilizzando JDBC.
 *
 * @field JdbcTemplate Template JDBC per la connessione alla base di dati
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class TelemetryDao implements ITelemetryDao {

    /**
     * Template JDBC per la connessione alla base di dati.
     */

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo TelemetryDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    TelemetryDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Metodo che implementa ITelemetryDao.persist(String, Telemetry).
     *
     * @param t Tracciamento da inserire.
     */

    public void persist(Telemetry t) {
        if (t.getEvents().iterator().hasNext()) {
            final String trackId = t.getTrack();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            tpl.update(new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO Telemetries(TrackId) VALUES (?)",
                                                                           new String[] {"Id"});
                        ps.setString(1, trackId);
                        return ps;
                    }
                },
                keyHolder);
            for (Date event : t.getEvents()) {
                tpl.update("INSERT INTO TelemetryEvents(TelemetryId, Date) " +
                           "VALUES(?, ?)",
                           new Object[] { keyHolder.getKey(),
                                          event
                           });
            }
        }
    }

    /**
     * Metodo che implementa ITelemetryDao.findAll(String).
     *
     * @param trackId Id del percorso per il quale si vuole ottenere la lista di tracciamenti.
     * @return Restituisce la lista dei tracciamenti per il percorso specificato.
     */

    public Iterable<Telemetry> findAll(final String trackId) {
        return tpl.query("SELECT Id FROM Telemetries WHERE TrackId = ?",
                         new Object[] {trackId},
                         new RowMapper<Telemetry>() {
                             @Override
                             public Telemetry mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 String id = rs.getString("Id");
                                 Iterable<Date> events =
                                     tpl.query("SELECT Date " +
                                               "FROM TelemetryEvents " +
                                               "WHERE TelemetryId = ?" +
                                               "ORDER BY Date",
                                               new Object[] {id},
                                               new RowMapper<Date>() {
                                                   @Override
                                                   public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
                                                       return rs.getTimestamp("Date");
                                                   }
                                               });
                                 return new Telemetry(events, trackId, id);
                             }
                         });
    }
}
