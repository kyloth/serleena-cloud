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
 * Name: DataRestController.java
 * Package: com.kyloth.serleenacloud.controller
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.WeatherForecast;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.sync.SyncOutputData;
import com.kyloth.serleenacloud.datamodel.sync.SyncInputData;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.auth.AuthToken;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;

/**
 * Controller REST per la gestione delle richieste riguardanti la gestione dei dati di sincronizzazione tra backend e applicativo android.
 *
 * @use Risponde alle richieste REST riguardanti la richiesta e offerta di dati di sincronizzazione, offrendo e ricevendo oggetti dal model che verranno automaticamente convertiti da o in JSON a Spring.
 * @field ds : IDataSource Campo dati statico contenente un oggetto che permette di interfacciarsi con il database tramite DAO
 * @field mapper : ObjectMapper Campo dati statico contenente un oggetto di utilità per la conversione tra oggetti java e costrutti json
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

@RestController
@RequestMapping("/data")
public class DataRestController {

    /**
     * Oggetto che permette di interfacciarsi con il database tramite oggetti DAO.
     */

    static IDataSource ds = DataSourceFactory.getDataSource();
    
    /**
     * Oggetto di utilità per la conversione tra oggetti java e costrutti json.
     */

    static ObjectMapper mapper = new ObjectMapper();

    /**
     * Metodo setter per il DataSource del controller.
     */

    void setDataSource(IDataSource ds) {
        this.ds = ds;
    }

    /**
     * Metodo che implementa la richiesta GET per ottenere la
     * lista di dati di sincronizzazione dal backend all'applicazione
     * android.
     *
     * @param authToken Token di riconoscimento.
     * @return Restituisce un oggetto di tipo SyncOutputData contenente i dati da sincronizzare.
     */

    @RequestMapping(method = RequestMethod.GET)
    public SyncOutputData get(@RequestHeader("X-AuthToken") String authToken) {

        AuthToken t = new AuthToken(authToken);
        User u = ds.userDao().find(t.getEmail());

        Iterable<Experience> es = ds.forUser(u).syncListDao().findAll();
        ArrayList<EmergencyContact> ec = new ArrayList<EmergencyContact>();
        ArrayList<WeatherForecast> wf = new ArrayList<WeatherForecast>();

        for (Experience e : es) {
            Rect r = e.getBoundingRect();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 5);

            for (WeatherForecast forecast : ds.weatherForecastDao().findAll(r, new Date(), cal.getTime()))
                wf.add(forecast);

            for (EmergencyContact contact : ds.emergencyContactDao().findAll(r))
                ec.add(contact);
        }

        return new SyncOutputData(es, wf, ec);
    }



    @ResponseStatus(HttpStatus.NO_CONTENT)
    class FailedSyncException extends RuntimeException {}

    /**
     * Metodo che implementa la richiesta POST per sincronizzare dati
     * dall'applicazione android al backend.
     *
     * @param id JSON rappresentante una lista di oggetti di tipo SyncInputData.
     * @param authToken Token di riconoscimento.
     */

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestParam("data") String id,
                     @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);
        try {
            for (SyncInputData input : mapper.readValue(id, SyncInputData[].class)) {

                Experience e = dataSource.experienceDao().find(input.getExperienceId());
                ArrayList<UserPoint> userPoints = new ArrayList<UserPoint>();

                for (UserPoint u : input.getUserPoints())
                    userPoints.add(u);
                for (UserPoint u : e.getUserPoints())
                    userPoints.add(u);

                Experience newE = new Experience(e.getName(), e.getId(), e.getBoundingRect(), e.getTracks(), userPoints, e.getPOIs());
                dataSource.experienceDao().persist(newE);

                for (Telemetry t : input.getTelemetryData()) {
                    dataSource.telemetryDao().persist(t);
                }
            }
        } catch (Exception e) {
            throw new FailedSyncException();
        }
    }

    /**
     * Metodo che implementa la richiesta GET per ottenere la lista
     * di esperienze in lista di sincronizzazione.
     *
     * @param authToken Token di riconoscimento.
     * @return Restituisce la lista dei nomi delle esperienze in lista di sincronizzazione.
     */

    @RequestMapping(value= "/sync", method = RequestMethod.GET)
    public Iterable<String> getSync(@RequestHeader("X-AuthToken") String authToken) {
        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());

        IDataSource dataSource = ds.forUser(user);

        ArrayList<String> syncList = new ArrayList<String>();
        for(Experience e : dataSource.syncListDao().findAll())
            syncList.add(e.getId());

        return syncList;
    }

    /**
     * Metodo che implementa la richiesta PUT per aggiungere esperienze
     * alla lista di sincronizzazione.
     *
     * @param body Mappa che contiene la lista delle esperienze da sincronizzare in formato JSON.
     * @param authToken Token di autenticazione.
     */

    @RequestMapping(value= "/sync", method = RequestMethod.PUT)
    public void putSync(@RequestBody MultiValueMap<String,String> body,
                        @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        String lists = body.getFirst("exp_list");

        try {
            String[] experiences = mapper.readValue(lists, String[].class);
            ArrayList<Experience> syncList = new ArrayList<Experience>();

            for (String e : experiences)
                syncList.add(dataSource.experienceDao().find(e));
            dataSource.syncListDao().persist(syncList);
        } catch (IOException e) {}
    }
}
