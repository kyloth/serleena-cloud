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
 * Name: TrackDaoIntegrationTest.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file e scrittura
 *                                       codice e documentazione Javadoc
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.Iterator;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kyloth.serleenacloud.persistence.ITrackDao;
import com.kyloth.serleenacloud.datamodel.business.CheckPoint;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.Track;

/**
 * Contiene test per la classe TelemetryDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class TrackDaoIntegrationTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static ITrackDao td;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insertUser = "INSERT INTO Users (Email, Password, DeviceId) VALUES ('foo@bar.com', 'psw', 'Kyloth-1');";
        String insertExperience = "INSERT INTO Experiences (Id, Name, User, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES ('id1', 'Experience_1', 'foo@bar.com', 1, 10, 10, 1), ('id2','Experience_2', 'foo@bar.com', 1, 10, 10, 1);";
        String insertExperienceTracks = "INSERT INTO ExperienceTracks (ExperienceId, TrackId) VALUES ('id1', 'id1'), ('id2', 'id2');";
        String insertTracks = "INSERT INTO Tracks (Id, Name) VALUES ('id1','Track_1'), ('id2', 'Track_2');";
        String insertCheckPoints = "INSERT INTO Checkpoints (TrackId, Longitude, Latitude, Idx) VALUES ('id1', 1, 1, 0), ('id2', 2, 2, 0);";
        String insertTelemetries = "INSERT INTO Telemetries (Id, TrackId) VALUES (0, 'id1'), (1, 'id2');";
        String insertTelEvents = "INSERT INTO TelemetryEvents (TelemetryId, Date) VALUES (0, '2015-01-01 00:00:01'), (1, '2015-01-01 00:00:01');";
        tpl.update(insertUser);
        tpl.update(insertExperience);
        tpl.update(insertTracks);
        tpl.update(insertExperienceTracks);
        tpl.update(insertCheckPoints);
        tpl.update(insertTelemetries);
        tpl.update(insertTelEvents);
        td = ds.trackDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo persist inserisca effettivamente il
     * Percorso fornito come parametro nel db.
     */

    @Test
    public void testPersist() {
        CheckPoint[] cp = {new CheckPoint(12, 13, 1), new CheckPoint(14, 15, 2)};
        Date[] events = {
            new Date()
        };
        Telemetry[] telemetries = {new Telemetry(events, "id3")};
        Track track = new Track("Track3", "id3", cp, telemetries);
        td.persist(track);
        String query = "SELECT Name FROM Tracks WHERE Id = 'id3';";
        String trackName = tpl.queryForObject(query, String.class);
        assertTrue(trackName.equals("Track3"));
    }

    /**
     * Verifica che il metodo findAll con parametro il nome di
     * un'Esperienza restituisca tutti i Percorsi relativi.
     */

    @Test
    public void testFindAllExperience() {
        Iterable<Track> tracks = td.findAll("id1");
        Iterator<Track> i_tracks = tracks.iterator();
        Track track = i_tracks.next();
        assertFalse(i_tracks.hasNext());
        assertTrue(track.getName().equals("Track_1"));
        Iterable<CheckPoint> checkPoints = track.getCheckPoints();
        Iterator<CheckPoint> i_check = checkPoints.iterator();
        assertTrue(i_check.next().getLatitude() == 1);
    }

    /**
     * Verifica che il metodo findAll chiamato senza parametri
     * restituisca tutti i Percorsi.
     */

    @Test
    public void testFindAllWithoutParam() {
        Iterable<Track> tracks = td.findAll();
        Iterator<Track> i_tracks = tracks.iterator();
        Track track_1 = i_tracks.next();
        Track track_2 = i_tracks.next();
        assertFalse(i_tracks.hasNext());
        assertTrue(track_1.getName().equals("Track_1"));
        assertTrue(track_2.getName().equals("Track_2"));
    }
}
