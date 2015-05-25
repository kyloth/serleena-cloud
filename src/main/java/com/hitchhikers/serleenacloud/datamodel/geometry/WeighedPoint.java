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
 * Name: WeighedPoint.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

/**
 * Classe rappresentante un punto geografico a cui e` associata
 * una piccola circonferenza
 *
 * @use Una collezione di WeighedPoint viene utilizzata dalla classe WideLine
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0 
 */

public class WeighedPoint extends Point {
    private double radius;


    /**
     * Crea un nuovo oggetto WeighedPoint data latitudine, longitudine e raggio associato
     *
     * @param Coordinata latitudinale del punto da creare
     * @param Coordinata longitudinale del punto da creare
     * @param Raggio associato al punto da creare
     */

    public WeighedPoint(double latitude, double longitude, double radius) {
        super(latitude, longitude);
        this.radius = radius;
    }

    /**
     * Restituisce il raggio della circonferenza associata
     *
     * @return Raggio della circonferenza associata
     */

    public double getRadius() {
        return radius;
    }
}
