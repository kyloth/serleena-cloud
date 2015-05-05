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
 * Date: 2015-05-05
 *
 * History:
 * Version  Programmer          Date        Changes
 * 1.0.0    Nicola Mometto      2015-05-05  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

import java.util.Arrays;

/**
 * Rappresenta una generica area rettangolare di mappa delimitata da
 * coordinate geografiche
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 * @since   2015-05-06
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
     * Ritorna true se il punto e` contenuto nell'area del rettangolo
     *
     * @param  Il punto da testare
     * @return True se il punto e` contenuto nel rettangolo, altrimenti false
     */

    public boolean contains(IPoint p) {
        return (p.getLatitude() <= nw.getLatitude() &&
                p.getLatitude() >= se.getLatitude() &&
                p.getLongitude() <= nw.getLongitude() &&
                p.getLongitude() >= se.getLongitude());
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
     * Ritorna true se il rettangolo corrente crea un'intersezione non vuota con
     * il poligono da testare
     *
     * @param  Il poligono da testare
     * @return True se l'intersezione tra i due poligoni e` non vuota, false altrimenti
     */

    public boolean intersects(IPoly p) {
        for (IPoint point : p.getPoints())
            if (contains(point))
                return true;
        return false;
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
