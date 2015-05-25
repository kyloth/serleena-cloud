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
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class ExperienceDao implements IExperienceDao {

    private JdbcTemplate tpl;
    private User user;
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
        String name = experience.getName();

        Rect r = experience.getBoundingRect();
        Point nw = r.getNWPoint();
        Point se = r.getSEPoint();

        if (find(name) != null) {
            delete(name);
        }

        tpl.update("INSERT INTO Experiences (Name, User, NWLongitude, NWLatitude, SELongitude, SELatitude) " +
                   "VALUES (?, ?, ?, ?, ?, ?)", new Object[] {name,
                           user.getEmail(),
                           nw.getLongitude(),
                           nw.getLatitude(),
                           se.getLongitude(),
                           se.getLatitude()
                                                             });
        for (Track t : experience.getTracks()) {
            tpl.update("DELETE FROM Tracks WHERE Name = ?", new Object[] {t.getName()});
            tpl.update("INSERT INTO Tracks(Name) VALUES (?)", new Object[] {t.getName()});
            tpl.update("INSERT INTO ExperienceTracks(ExperienceName, TrackName) VALUES(?, ?)",
                       new Object[] {name, t.getName()});
        }

        for (UserPoint p : experience.getUserPoints())
            tpl.update("INSERT INTO ExperienceUserPoints(ExperienceName, Name, Longitude, Latitude) VALUES (?, ?, ?, ?) ",
                       new Object[] {name, p.getName(), p.getLongitude(), p.getLatitude()});


        for (PointOfInterest p : experience.getPOIs())
            tpl.update("INSERT INTO ExperiencePOIs(ExperienceName, POIName) VALUES(?, ?)",
                       new Object[] {name, p.getName()});

    }

    /**
     * Metodo che implementa IExperienceDao.delete(String).
     *
     * @param name Nome dell'Esperienza da eliminare.
     */

    public void delete(String name) {
        tpl.update("DELETE FROM SyncList WHERE ExperienceName = ?", new Object[] {name});
        tpl.update("DELETE FROM ExperienceTracks WHERE ExperienceName = ?", new Object[] {name});
        tpl.update("DELETE FROM ExperiencePOIs WHERE ExperienceName = ?", new Object[] {name});
        tpl.update("DELETE FROM ExperienceUserPoints WHERE ExperienceName = ?", new Object[] {name});
        tpl.update("DELETE FROM Experiences WHERE Name = ?", new Object[] {name});
    }

    /**
     * Metodo che implementa IExperienceDao.find(String).
     *
     * @param name Nome dell'Esperienza da ottenere.
     * @return Restituisce l'Esperienza cercata, se presente.
     */

    public Experience find(String name) {
        for (Experience e : findAll())
            if (e.getName().equals(name))
                return e;
        return null;
    }

    /**
     * Metodo che implementa IExperienceDao.findAll().
     *
     * @return Restituisce la lista delle Esperienze relative all'utente.
     */

    public Iterable<Experience> findAll() {
        return tpl.query("SELECT Name, NWLongitude, NWLatitude, SELongitude, SELatitude " +
                         "FROM Experiences " +
                         "WHERE User =  ? ",
                         new Object[] {user.getEmail()},
        new RowMapper<Experience>() {
            @Override
            public Experience mapRow(ResultSet rs, int rowNum) throws SQLException {
                String eName = rs.getString("Name");
                return new Experience(eName,
                                      new Rect(new Point(rs.getDouble("NWLatitude"),
                                               rs.getDouble("NWLongitude")),
                                               new Point(rs.getDouble("SELatitude"),
                                                       rs.getDouble("SELongitude"))),
                                      tDao.findAll(eName),
                                      tpl.query("SELECT Name, Longitude, Latitude " +
                                                "FROM ExperienceUserPoints " +
                                                "WHERE ExperienceName =  ? ",
                                                new Object[] {eName},
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
                          "WHERE ep.ExperienceName =  ?  AND ep.POIName = p.Name",
                          new Object[] {eName},
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
