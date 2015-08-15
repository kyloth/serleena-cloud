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
 * Name: ExperienceRestController.java
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.business.Telemetry;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.auth.AuthToken;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Controller REST per la gestione delle richieste CRUD riguardanti la gestione delle Esperienze.
 *
 * @use Risponde alle richieste REST riguardanti le Esperienze, ritornando oggetti del model che Spring convertirà automaticamente in risposte JSON.
 * @field ds : IDataSource Campo dati statico contenente un oggetto che permette di interfacciarsi con il database tramite oggetti DAO
 * @field mapper : ObjectMapper Campo dati statico contenente un oggetto di utilità per la conversione tra oggetti java e costrutti json
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

@RestController
@RequestMapping("/experiences")
public class ExperienceRestController {
    
    /**
     * Oggetto che permette di interfacciarsi con il database tramite oggetti DAO.
     */

    static IDataSource ds = DataSourceFactory.getDataSource();
    
    /**
     * Oggetto di utilità per la conversione tra oggetti java e costrutti json.
     */

    static ObjectMapper mapper = new ObjectMapper();

    static void setDataSource(IDataSource ds) {
        ExperienceRestController.ds = ds;
    }

    /**
     * Implementa la richiesta GET per ottenere la lista dei nomi delle
     * Esperienze di un utente.
     *
     * @param authToken Token di riconoscimento.
     * @return Restituisce la lista degli id e dei nomi delle Esperienze di un utente.
     */

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<String[]> list(@RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        ArrayList<String[]> experiences = new ArrayList<String[]>();
        for(Experience e : dataSource.experienceDao().findAll())
            experiences.add(new String[]{e.getId(), e.getName()});

        return experiences;
    }

    /**
     * Metodo che implementa la richiesta GET per ottenere
     * un'Esperienza esistente.
     *
     * @param id id dell'Esperienza da ottenere..
     * @param authToken Token di riconoscimento.
     */

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public Experience get(@PathVariable("id") String id,
                          @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        return dataSource.experienceDao().find(id);
    }

    /**
     * Metodo che implementa la richiesta DELETE per cancellare
     * un'Esperienza esistente.
     *
     * @param id Nome dell'Esperienza da cancellare.
     * @param authToken Token di riconoscimento.
     */

    @RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id,
                       @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        dataSource.experienceDao().delete(id);
    }

    /**
     * Metodo che implementa la richiesta POST per l'inserimento di
     * una nuova Esperienza.
     *
     * @param name Nome della nuova Esperienza.
     * @param pois Lista dei nomi dei Punti di Interesse in formato JSON.
     * @param ups Lista dei nomi dei Punti Utente in formato JSON.
     * @param tracks Lista dei Percorsi in formato JSON.
     * @param from Punto che delimita la regione dell'Esperienza all'angolo nord ovest.
     * @param to Punto che delimita la regione dell'Esperienza all'angolo sud-est.
     * @param authToken Token di riconoscimento.
     * @return Restituisce l'id dell'Esperienza create
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestParam("name") String name,
                         @RequestParam("points_of_interest") String pois,
                         @RequestParam("user_points") String ups,
                         @RequestParam("tracks") String tracks,
                         @RequestParam("from") String from,
                         @RequestParam("to") String to,
                         @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);
        try {

            PointOfInterest[] _pois = mapper.readValue(pois, PointOfInterest[].class);
            UserPoint[] _ups = mapper.readValue(ups, UserPoint[].class);
            Track[] _tracks = mapper.readValue(tracks, Track[].class);
            Point _from = mapper.readValue(from, Point.class);
            Point _to = mapper.readValue(to, Point.class);

            String id = UUID.randomUUID().toString();
            Experience experience = new Experience(name, id, new Rect(_from, _to), _tracks, _ups, _pois);
            dataSource.experienceDao().persist(experience);
            return id;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Metodo che implementa la richiesta PUT per modificare una
     * Esperienza esistente.
     *
     * @param id id dell'Esperienza da aggiornare.
     * @param body Mappa contenente i dati necessari all'aggiornamento in formato JSON.
     * @param authToken Token di riconoscimento.
     */

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") String id,
                       @RequestBody MultiValueMap<String,String> body,
                       @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        try {
            String name = body.getFirst("name");
            PointOfInterest[] pois = mapper.readValue(body.getFirst("points_of_interest"), PointOfInterest[].class);
            UserPoint[] ups = mapper.readValue(body.getFirst("user_points"), UserPoint[].class);
            Track[] tracks = mapper.readValue(body.getFirst("tracks"), Track[].class);
            Point from = mapper.readValue(body.getFirst("from"), Point.class);
            Point to = mapper.readValue(body.getFirst("to"), Point.class);

            Experience experience = new Experience(name, id, new Rect(from, to), tracks, ups, pois);
            dataSource.experienceDao().persist(experience);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo che implementa la richiesta GET per ottenere un Percorso
     * al suo nome e al nome dell'Esperienza relativa.
     *
     * @param id Nome dell'Esperienza cui il Percorso è relativo.
     * @param track_id Nome del Percorso da ottenere.
     * @return Restituisce un oggetto Track rappresentante il Percorso richiesto.
     */

    @RequestMapping(value= "/{id}/tracks/{track_id}", method = RequestMethod.GET)
    public Track get(@PathVariable("id") String id,
                     @PathVariable("track_id") String track_id,
                     @RequestHeader("X-AuthToken") String authToken) {

        AuthToken token = new AuthToken(authToken);
        User user = ds.userDao().find(token.getEmail());
        IDataSource dataSource = ds.forUser(user);

        for(Track t : dataSource.trackDao().findAll(id))
            if (t.getName().equals(track_id))
                return t;

        return null;
    }

    @RequestMapping(value= "/{id}/tracks/{track_id}/telemetries", method = RequestMethod.GET)
    public Iterable<Telemetry> getTelemetriesList(@PathVariable("id") String id,
                                                  @PathVariable("track_id") String track_id,
                                                  @RequestHeader("X-AuthToken") String authToken) {
        return get(id, track_id, authToken).getTelemetries();
    }


    @RequestMapping(value= "/{id}/tracks/{track_id}/telemetries/{telemetry_id}", method = RequestMethod.GET)
    public Telemetry getTelemetry(@PathVariable("id") String id,
                                  @PathVariable("track_id") String track_id,
                                  @PathVariable("telemetry_id") String telemetry_id,
                                  @RequestHeader("X-AuthToken") String authToken) {

        Track track = get(id, track_id, authToken);
        for(Telemetry t : track.getTelemetries())
            if(t.getId().equals(telemetry_id))
                return t;

        return null;
    }
}
