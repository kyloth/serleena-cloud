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

/**
 * Rappresenta una generica area rettangolare di mappa delimitata da
 * coordinate geografiche
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Rect implements IRect {

    private IPoint nw;
    private IPoint se;

    private Iterable<IPoint> points;

    /**
     * Costruisce un nuovo oggetto Rect dati i due punti geografici degli angoli
     * Nord-Ovest e Sud-Est
     *
     * @param Punto geografico dell'angolo Nord-Ovest
     * @param Punto geografico dell'angolo Sud-Est
     */

    public Rect(IPoint nw, IPoint se) {
        this.nw = nw;
        this.se = se;
    }

    /**
     * Ritorna la collezione dei punti che delimitano il perimetro del rettangolo
     *
     * @return La collezione dei punti che delimitano il perimetro del rettangolo
     */

    public Iterable<IPoint> getPoints() {
        if (points == null) {
            Point ne = new Point(nw.getLatitude(), se.getLongitude());
            Point sw = new Point(se.getLatitude(), nw.getLongitude());
            points = Arrays.asList(new IPoint[] {nw, ne, se, sw });
        }
        return points;
    }

    /**
     * Ritorna la coordinata geografica dell'angolo Nord-Ovest del rettangolo
     *
     * @return La coordinata geografica dell'angolo Nord-Ovest
     */

    public IPoint getNWPoint() {
        return nw;
    }

    /**
     * Ritorna la coordinata geografica dell'angolo Sud-Est del rettangolo
     *
     * @return La coordinata geografica dell'angolo Sud-Est
     */

    public IPoint getSEPoint() {
        return se;
    }

}
