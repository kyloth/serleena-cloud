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
 * Name: MiscRestController.java
 * Package: com.kyloth.serleenacloud.controller
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.auth.TempToken;

import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.business.Path;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

/**
 * Controller REST per la gestione di alcune richiesta di utilità generale.
 *
 * @use Risponde alle richieste REST riguardanti la richiesta e offerta di dati di sincronizzazione, offrendo e ricevendo oggetti dal model che verranno automaticamente convertiti da o in JSON a Spring.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

@RestController
public class MiscRestController {

    static IDataSource ds = DataSourceFactory.getDataSource();

    static void setDataSource(IDataSource ds) {
        MiscRestController.ds = ds;
    }

    /**
     * Metodo che implementa la richiesta GET per ottenere un
     * token temporaneo per il pairing tra applicazione android
     * e backend.
     *
     * @param id Id del dispositivo di cui si vuole effettuare il pairing.
     * @return Restituisce un token temporaneo per il dispositivo.
     */

    @RequestMapping(value= "/tokens/{kyloth_id}", method = RequestMethod.GET)
    public String token(@PathVariable("kyloth_id") String id) {
        TempToken t = new TempToken(id);
        ds.tempTokenDao().persist(t);
        System.err.println(t.getToken());
        return t.getToken();
    }

    /**
     * Metodo che implementa la richiesta GET per ottenere tutti i Punti
     * di Interesse compresi in una certa area di mappa.
     *
     * @param from Punto a nord ovest della regione di interesse, in formato JSON.
     * @param to Punto a sud est della regione di interesse, in formato JSON.
     * @return Restituisce la lista dei Punti di Interesse relativi alla regione specificata.
     */

    @RequestMapping(value= "/poi/{from}/{to}", method = RequestMethod.GET)
    public Iterable<PointOfInterest> poi(@PathVariable("from") String from,
                                         @PathVariable("to") String to) {
        String[] _from = from.split(",");
        String[] _to = to.split(",");

        Rect r = new Rect(new Point(Double.valueOf(_from[0]),
                                    Double.valueOf(_from[1])),
                          new Point(Double.valueOf(_to[0]),
                                    Double.valueOf(_to[1])));

        return ds.pointOfInterestDao().findAll(r);
    }

    /**
     * Metodo che implementa la richiesta GET per ottenere tutti i sentieri
     * relativi a una certa area di mappa.
     *
     * @param from Punto a nord ovest della regione di interesse, in formato JSON.
     * @param to Punto a sud est della regione di interesse, in formato JSON.
     * @return Restituisce la lista dei sentieri relativi alla regione specificata.
     */

    @RequestMapping(value= "/paths/{from}/{to}", method = RequestMethod.GET)
    public Iterable<Path> paths(@PathVariable("from") String from,
                                @PathVariable("to") String to) {
        String[] _from = from.split(",");
        String[] _to = to.split(",");

        Rect r = new Rect(new Point(Double.valueOf(_from[0]),
                                    Double.valueOf(_from[1])),
                          new Point(Double.valueOf(_to[0]),
                                    Double.valueOf(_to[1])));

        return ds.pathDao().findAll(r);
    }

}
