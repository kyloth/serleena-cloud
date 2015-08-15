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
 * Name: ExperienceDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.persistence.IExperienceDao;
import com.kyloth.serleenacloud.persistence.ITrackDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza IExperienceDao per database MySQL utilizzando JDBC.
 *
 * @field tpl : JdbcTemplate Template JDBC per la connessione alla base di dati
 * @field user : User Oggetto che rappresenta l'utente per il quale si vogliono ottenere dati dal database
 * @field tDao : ITrackDao DAO per agire su oggetti di tipo Track nel database
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class ExperienceDao implements IExperienceDao {

    /**
     * Template JDBC per la connessione alla base di dati.
     */

    private JdbcTemplate tpl;
    
    /**
     * Utente per il quale si vogliono ottenere dati dal database.
     */

    private User user;
    
    /**
     * DAO per gli oggetti di tipo Track.
     */

    private ITrackDao tDao;

    /**
     * Costruisce un nuovo ExperienceDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    ExperienceDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
        this.user = ds.getUser();
        this.tDao = ds.trackDao();
    }

    /**
     * Metodo che implementa IExperienceDao.persist(Experience).
     *
     * @param experience Esperienza da inserire.
     */

    public void persist(Experience experience) {
        String id = experience.getId();

        Rect r = experience.getBoundingRect();
        Point nw = r.getNWPoint();
        Point se = r.getSEPoint();

        if (find(id) != null) {
            System.err.println("deleting");
            System.err.println(find(id));
            delete(id);
        }

        tpl.update("INSERT INTO Experiences (Id, Name, User, NWLongitude, NWLatitude, SELongitude, SELatitude) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?)", new Object[] {id,
                                                              experience.getName(),
                                                              user.getEmail(),
                                                              nw.getLongitude(),
                                                              nw.getLatitude(),
                                                              se.getLongitude(),
                                                              se.getLatitude()
                   });
        for (Track t : experience.getTracks()) {
            tpl.update("DELETE FROM Tracks WHERE Name = ?", new Object[] {t.getName()});
            tDao.persist(t);
            tpl.update("INSERT INTO ExperienceTracks(ExperienceId, TrackName) VALUES(?, ?)",
                       new Object[] {id, t.getName()});
        }

        for (UserPoint p : experience.getUserPoints())
            tpl.update("INSERT INTO ExperienceUserPoints(ExperienceId, Name, Longitude, Latitude) VALUES (?, ?, ?, ?) ",
                       new Object[] {id, p.getName(), p.getLongitude(), p.getLatitude()});


        for (PointOfInterest p : experience.getPOIs())
            tpl.update("INSERT INTO ExperiencePOIs(ExperienceId, POIName) VALUES(?, ?)",
                       new Object[] {id, p.getName()});

    }

    /**
     * Metodo che implementa IExperienceDao.delete(String).
     *
     * @param name Nome dell'Esperienza da eliminare.
     */

    public void delete(String id) {
        tpl.update("DELETE FROM SyncList WHERE ExperienceId = ?", new Object[] {id});
        tpl.update("DELETE FROM ExperienceTracks WHERE ExperienceId = ?", new Object[] {id});
        tpl.update("DELETE FROM ExperiencePOIs WHERE ExperienceId = ?", new Object[] {id});
        tpl.update("DELETE FROM ExperienceUserPoints WHERE ExperienceId = ?", new Object[] {id});
        tpl.update("DELETE FROM Experiences WHERE Id = ?", new Object[] {id});
    }

    /**
     * Metodo che implementa IExperienceDao.find(String).
     *
     * @param name Nome dell'Esperienza da ottenere.
     * @return Restituisce l'Esperienza cercata, se presente.
     */

    public Experience find(String id) {
        for (Experience e : findAll())
            if (e.getId().equals(id))
                return e;
        return null;
    }

    /**
     * Metodo che implementa IExperienceDao.findAll().
     *
     * @return Restituisce la lista delle Esperienze relative all'utente.
     */

    public Iterable<Experience> findAll() {
        return tpl.query("SELECT Id, Name, NWLongitude, NWLatitude, SELongitude, SELatitude " +
                         "FROM Experiences " +
                         "WHERE User =  ? ",
                         new Object[] {user.getEmail()},
        new RowMapper<Experience>() {
            @Override
            public Experience mapRow(ResultSet rs, int rowNum) throws SQLException {
                String eId = rs.getString("Id");
                return new Experience(rs.getString("Name"), eId,
                                      new Rect(new Point(rs.getDouble("NWLatitude"),
                                               rs.getDouble("NWLongitude")),
                                               new Point(rs.getDouble("SELatitude"),
                                                       rs.getDouble("SELongitude"))),
                                      tDao.findAll(eId),
                                      tpl.query("SELECT Name, Longitude, Latitude " +
                                                "FROM ExperienceUserPoints " +
                                                "WHERE ExperienceId =  ? ",
                                                new Object[] {eId},
                new RowMapper<UserPoint>() {
                    @Override
                    public UserPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new UserPoint(rs.getDouble("Latitude"),
                                             rs.getDouble("Longitude"),
                                             rs.getString("Name"));
                    }
                }),
                tpl.query("SELECT Name, Longitude, Latitude, Type " +
                          "FROM ExperiencePOIs ep, POIs p " +
                          "WHERE ep.ExperienceId =  ?  AND ep.POIName = p.Name",
                          new Object[] {eId},
                new RowMapper<PointOfInterest>() {
                    @Override
                    public PointOfInterest mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new PointOfInterest(rs.getDouble("Latitude"),
                                                   rs.getDouble("Longitude"),
                                                   rs.getString("Name"),
                                                   PointOfInterest.POIType.valueOf(rs.getString("Type")));
                    }
                }));
            }
        });
    }
}
