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

import com.kyloth.serleenacloud.datamodel.business.Telemetry;

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
        Date[] events_1 = {
            new Date(),
            new Date(),
            new Date(),
            new Date()
        };
        Date[] events_2 = {
            new Date(),
            new Date(),
            new Date(),
            new Date()
        };
        Date[] events_3 = {
            new Date(),
            new Date(),
            new Date(),
            new Date()
        };
        Telemetry t1 = new Telemetry(events_1, "track");
        Telemetry t2 = new Telemetry(events_2, "track");
        Telemetry t3 = new Telemetry(events_3, "track");
        td.persist("Track1", t1);
        td.persist("Track1", t2);
        td.persist("Track2", t3);
        Iterable<Telemetry> telemetries_1 = td.findAll("Track1");
        Iterable<Telemetry> telemetries_2 = td.findAll("Track2");
        Iterable<Telemetry> telemetries_3 = td.findAll("FalseTrack");
        Iterator<Telemetry> i_t_1 = telemetries_1.iterator();
        Iterator<Telemetry> i_t_2 = telemetries_2.iterator();
        Iterator<Telemetry> i_t_f = telemetries_3.iterator();
        assertFalse(i_t_f.hasNext());
        Telemetry tel_1 = i_t_1.next();
        Telemetry tel_2 = i_t_1.next();
        Telemetry tel_3 = i_t_2.next();
        assertFalse(i_t_1.hasNext());
        assertFalse(i_t_2.hasNext());
    }
}
