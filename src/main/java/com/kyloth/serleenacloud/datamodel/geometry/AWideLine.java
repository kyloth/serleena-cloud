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
 * Name: AWideLine.java
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
 * Rappresenta una generica entita` di mappa composta da una collezione ordinata
 * di WeighedPoint
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public abstract class AWideLine {

    private Iterable<WeighedPoint> points;

    /**
     * Crea un nuovo oggetto AWideLine
     *
     * @param  La collezione di oggetti WeighedPoint associati
     */

    public AWideLine(Iterable<WeighedPoint> points) {
        this.points = points;
    }
    /**
     * Crea un nuovo oggetto AWideLine
     *
     * @param  Array di oggetti WeighedPoint associati
     */

    public AWideLine(WeighedPoint[] points) {
        this.points = Arrays.asList(points);
    }

    /**
     * Ritorna la collezione di oggetti WeighedPoint associata
     *
     * @return La collezione di oggetti WeighedPoint
     */

    public Iterable<WeighedPoint> getPoints() {
        return points;
    }

}