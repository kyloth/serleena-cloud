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
 * Name: TelemetryDaoTest.java
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

import com.kyloth.serleenacloud.persistence.ITelemetryDao;

import com.kyloth.serleenacloud.datamodel.business.ITelemetry;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.TelemetryEvent;

/**
 * Contiene test per la classe TelemetryDao.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class TelemetryDaoTest {
    private static ApplicationContext context;
    private static JDBCDataSource ds;
    private static JdbcTemplate tpl;
    private static ITelemetryDao td;

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() {
        context = new ClassPathXmlApplicationContext("Spring-ModuleTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        tpl = ds.getTpl();
        String insertTracks = "INSERT INTO Tracks (Name) VALUES ('Track1'), ('Track2');";
        tpl.update(insertTracks);
        td = ds.telemetryDao();
    }

    /**
     * Rilascia l'ApplicationContext per i test successivi.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Verifica che il metodo persist inserisca correttamente i Tracciamenti forniti
     * come parametro e che il metodo findAll restituisca i Tracciamenti associati
     * al Percorso il cui nome è fornito come parametro.
     */

    @Test
    public void testPersistAndFindAll() {
        TelemetryEvent[] events_1 = {
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 1),
            new TelemetryEvent(TelemetryEvent.EventType.HEART, new Date(), 2),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 3),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 4)
        };
        TelemetryEvent[] events_2 = {
            new TelemetryEvent(TelemetryEvent.EventType.HEART, new Date(), 5),
            new TelemetryEvent(TelemetryEvent.EventType.HEART, new Date(), 6),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 7),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 8)
        };
        TelemetryEvent[] events_3 = {
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 9),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 10),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 11),
            new TelemetryEvent(TelemetryEvent.EventType.CHECKPOINT, new Date(), 12)
        };
        Telemetry t1 = new Telemetry(events_1);
        Telemetry t2 = new Telemetry(events_2);
        Telemetry t3 = new Telemetry(events_3);
        td.persist("Track1", t1);
        td.persist("Track1", t2);
        td.persist("Track2", t3);
        Iterable<ITelemetry> telemetries_1 = td.findAll("Track1");
        Iterable<ITelemetry> telemetries_2 = td.findAll("Track2");
        Iterable<ITelemetry> telemetries_3 = td.findAll("FalseTrack");
        Iterator<ITelemetry> i_t_1 = telemetries_1.iterator();
        Iterator<ITelemetry> i_t_2 = telemetries_2.iterator();
        Iterator<ITelemetry> i_t_f = telemetries_3.iterator();
        assertFalse(i_t_f.hasNext());
        Telemetry tel_1 = (Telemetry) i_t_1.next();
        Telemetry tel_2 = (Telemetry) i_t_1.next();
        Telemetry tel_3 = (Telemetry) i_t_2.next();
        assertFalse(i_t_1.hasNext());
        assertFalse(i_t_2.hasNext());
        Iterable<TelemetryEvent> t_events_1 = tel_1.getEvents();
        Iterable<TelemetryEvent> t_events_2 = tel_2.getEvents();
        Iterable<TelemetryEvent> t_events_3 = tel_3.getEvents();
        Iterator<TelemetryEvent> i_e_1 = t_events_1.iterator();
        Iterator<TelemetryEvent> i_e_2 = t_events_2.iterator();
        Iterator<TelemetryEvent> i_e_3 = t_events_3.iterator();
        assertTrue(i_e_1.next().getValue() == 1);
        assertTrue(i_e_1.next().getValue() == 2);
        assertTrue(i_e_1.next().getValue() == 3);
        assertTrue(i_e_1.next().getValue() == 4);
        assertTrue(i_e_2.next().getValue() == 5);
        assertTrue(i_e_2.next().getValue() == 6);
        assertTrue(i_e_2.next().getValue() == 7);
        assertTrue(i_e_2.next().getValue() == 8);
        assertTrue(i_e_3.next().getValue() == 9);
        assertTrue(i_e_3.next().getValue() == 10);
        assertTrue(i_e_3.next().getValue() == 11);
        assertTrue(i_e_3.next().getValue() == 12);
    }
}
