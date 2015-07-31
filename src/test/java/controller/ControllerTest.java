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
 * Name: ControllerTest.java
 * Package: com.kyloth.serleenacloud.controller
 * Author: Gabriele Pozzan
 *
 * History:
 * Version  Programmer       Changes
 * 1.0.0    Gabriele Pozzan  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.controller;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Arrays;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;

import com.kyloth.serleenacloud.datamodel.auth.AuthToken;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.business.*;
import com.kyloth.serleenacloud.datamodel.sync.*;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.sync.SyncOutputData;
import com.kyloth.serleenacloud.datamodel.sync.SyncInputData;
import com.kyloth.serleenacloud.persistence.jdbc.JDBCDataSource;
import com.kyloth.serleenacloud.persistence.jdbc.UserDao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Contiene test per il package com.kyloth.serleenacloud.controller.
 * Tali test fungono da test di integrazione tra i package
 * com.kyloth.serleenacloud.datamodel e com.kyloth.serleenacloud.persistence.
 *
 * @author Gabriele Pozzan <gabriele.pozzan@studenti.unipd.it>
 * @version 1.0.0
 */

public class ControllerTest {
    private static ApplicationContext context;
    private static DataRestController drc;
    private static UserRestController ur;
    private static MiscRestController mrc;
    private static ExperienceRestController erc;
    private static JDBCDataSource ds;
    private static JDBCDataSource ds_user;
    private static AuthToken token;

    static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    /**
     * Inizializza i campi dati necessari alla conduzione dei test.
     */

    @BeforeClass
    public static void initialize() throws Exception {
        context = new ClassPathXmlApplicationContext("Spring-ModuleControllerTest.xml");
        ds = (JDBCDataSource) context.getBean("dataSource");
        drc = new DataRestController();
        ur = new UserRestController();
        mrc = new MiscRestController();
        erc = new ExperienceRestController();
        drc.setDataSource(ds);
        ur.setDataSource(ds);
        mrc.setDataSource(ds);
        erc.setDataSource(ds);
    }

    /**
     * Libera il contesto per successivi test.
     */

    @AfterClass
    public static void cleanUp() {
        ((ConfigurableApplicationContext)context).close();
    }

    /**
     * Testa le funzionalità offerte dal package controller, partendo
     * dall'autenticazione e pairing fino ad arrivare alla sincronizzazione.
     */

    @Test
    public void controllerTest() throws com.fasterxml.jackson.core.JsonProcessingException {
        /**
         * Crea un utente con email "user1@serleena.com", password "psw1"
         * e id dispositivo "Kyloth-1".
         */
        UserDao ud = (UserDao) ds.userDao();
        // /user
        ur.create("user1@serleena.com", "psw1");
        assertTrue(ud.find("user1@serleena.com").getEmail().equals("user1@serleena.com"));
        // /user/token
        String authToken = ur.token("user1@serleena.com::psw1");
        // /token/:kyloth_id:
        String tempToken = mrc.token("Kyloth-1");
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("temp_token", tempToken);
        // /user/pair
        ur.pair(authToken, body);
        assertTrue(ud.find("user1@serleena.com").getDeviceId().equals("Kyloth-1"));
        // /user/pair/:temp_token:
        String returnAuthToken = ur.pair(tempToken);
        assertTrue(returnAuthToken.equals(authToken));

        /**
         * Crea i dati per una nuova esperienza da inserire nel db.
         */
        CheckPoint check_point_1 = new CheckPoint(2, 2, 0);
        CheckPoint check_point_2 = new CheckPoint(3, 3, 1);
        Date event_1 = new Date();
        Telemetry telemetry_1 = new Telemetry(Arrays.asList(new Date[] {event_1}),
                                              "Track_1");
        Track track_1 = new Track("Track_1",
                                  new CheckPoint[] {check_point_1, check_point_2},
                                  new Telemetry[] {telemetry_1});
        UserPoint user_point_1 = new UserPoint(4, 4, "UP1");
        UserPoint user_point_2 = new UserPoint(5, 5, "UP2");
        PointOfInterest poi_1 = new PointOfInterest(6, 6, "POI1",
                PointOfInterest.POIType.INFO);
        PointOfInterest poi_2 = new PointOfInterest(7, 7, "POI2",
                PointOfInterest.POIType.FOOD);
        Point from = new Point(10, 1);
        Point to = new Point(1, 10);
        /**
         * Testa inserimento e recupero e cancellazione di un'esperienza.
         */
        // /experience POST
        erc.create("Experience_1",
                   ow.writeValueAsString(new PointOfInterest[] {poi_1, poi_2}),
                   ow.writeValueAsString(new UserPoint[] {user_point_1, user_point_2}),
                   ow.writeValueAsString(new Track[] {track_1}),
                   ow.writeValueAsString(from),
                   ow.writeValueAsString(to),
                   authToken);
        assertTrue(erc.list(authToken).iterator().next().equals("Experience_1"));
        // /experience/:id: GET
        Experience experience = erc.get("Experience_1", authToken);
        assertTrue(experience.getName().equals("Experience_1"));
        assertTrue(experience.getTracks().iterator().next().getName().equals("Track_1"));
        assertTrue(experience.getUserPoints().iterator().next().getName().equals("UP1"));
        assertTrue(experience.getPOIs().iterator().next().getName().equals("POI1"));
        // /experience/:exp_id: DELETE
        erc.delete("Experience_1", authToken);
        assertFalse(erc.list(authToken).iterator().hasNext());

        /**
         * Testa il recupero delle informazioni riguardanti un percorso.
         */
        // /experience/:exp-id:/tracks/:track-id: GET
        erc.create("Experience_1",
                   ow.writeValueAsString(new PointOfInterest[] {poi_1, poi_2}),
                   ow.writeValueAsString(new UserPoint[] {user_point_1, user_point_2}),
                   ow.writeValueAsString(new Track[] {track_1}),
                   ow.writeValueAsString(from),
                   ow.writeValueAsString(to),
                   authToken);
        Track track = erc.get("Experience_1", "Track_1", authToken);
        assertTrue(track.getName().equals("Track_1"));

        /**
         * Testa le funzionalità di DataRestController.
         */
        LinkedMultiValueMap<String, String> syncBody = new LinkedMultiValueMap<String, String>();
        syncBody.add("exp_list", "[\"Experience_1\"]");
        // /data/sync PUT
        drc.putSync(syncBody, authToken);
        // /data/sync GET
        Iterable<String> syncList = drc.getSync(authToken);
        Iterator<String> i_sync = syncList.iterator();
        assertTrue(i_sync.next().equals("Experience_1"));
        // /data/sync GET
        SyncOutputData sod = drc.get(authToken);
        Iterable<Experience> sod_experiences = sod.getExperiences();
        Iterator<Experience> i_sod_experiences = sod_experiences.iterator();
        assertTrue(i_sod_experiences.next().getName().equals("Experience_1"));
        Iterable<EmergencyContact> sod_emergency = sod.getEmergencyData();
        Iterator<EmergencyContact> i_sod_emergency = sod_emergency.iterator();
        assertTrue(i_sod_emergency.next().getName().equals("Emergency_1"));
        Date event_2 = new Date();
        Telemetry telemetry_2 = new Telemetry(Arrays.asList(new Date[] {event_2}),
                                              "Track_1");
        UserPoint user_point_3 = new UserPoint(1.5, 1.5, "UP3");
        SyncInputData sid = new SyncInputData("Experience_1",
                                              new UserPoint[] {user_point_3},
                                              new Telemetry[] {telemetry_2});
        drc.put(ow.writeValueAsString(new SyncInputData[] {sid}), authToken);
        Experience sid_experience = erc.get("Experience_1", authToken);
        Iterable<UserPoint> sid_up = sid_experience.getUserPoints();
        Iterator<UserPoint> i_sid_up = sid_up.iterator();
        assertTrue(i_sid_up.next().getName().equals("UP3"));
        assertTrue(i_sid_up.next().getName().equals("UP1"));
        assertTrue(i_sid_up.next().getName().equals("UP2"));
        assertTrue(mrc.paths("45.31,10.65", "44.28,11.71").iterator().next().getName().equals("foo"));
    }

}
