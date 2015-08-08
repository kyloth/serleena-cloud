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
 * Name: Rect.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe rappresentante un'area rettangolare delimitata da coordinate geografiche.
 *
 * @use Contiene metodi per ottenere i due vertici da cui è delimitato il rettangolo. Viene utilizzata da numerose classi nel data model quando è necessario rappresentare una regione di mappa.
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0 
 */

public class Rect {

    private Point nw;
    private Point se;

    private Iterable<Point> points;

    /**
     * Costruisce un nuovo oggetto Rect dati i due punti geografici degli angoli
     * Nord-Ovest e Sud-Est
     *
     * @param Punto geografico dell'angolo Nord-Ovest
     * @param Punto geografico dell'angolo Sud-Est
     */

    public Rect(Point nw, Point se) {
        assert(nw.getLatitude() >= se.getLatitude());
        assert(nw.getLongitude() <= se.getLongitude());
        this.nw = nw;
        this.se = se;
    }

    /**
     * Ritorna la collezione dei punti che delimitano il perimetro del rettangolo
     *
     * @return La collezione dei punti che delimitano il perimetro del rettangolo
     */

    @JsonIgnore
    public Iterable<Point> getPoints() {
        if (points == null) {
            Point ne = new Point(nw.getLatitude(), se.getLongitude());
            Point sw = new Point(se.getLatitude(), nw.getLongitude());
            points = Arrays.asList(new Point[] {nw, ne, se, sw });
        }
        return points;
    }

    /**
     * Ritorna la coordinata geografica dell'angolo Nord-Ovest del rettangolo
     *
     * @return La coordinata geografica dell'angolo Nord-Ovest
     */

    @JsonGetter("topLeft")
    public Point getNWPoint() {
        return nw;
    }

    /**
     * Ritorna la coordinata geografica dell'angolo Sud-Est del rettangolo
     *
     * @return La coordinata geografica dell'angolo Sud-Est
     */

    @JsonGetter("bottomRight")
    public Point getSEPoint() {
        return se;
    }

}
