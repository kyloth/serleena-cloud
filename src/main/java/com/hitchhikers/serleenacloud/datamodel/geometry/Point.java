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
 * Name: Point.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

/**
 * Rappresenta un generico punto geografico
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Point implements IPoint {
    private double latitude;
    private double longitude;


    /**
     * Crea un nuovo generico punto geografico, data latitudine e longitudine
     *
     * @param  latitude Coordinata latitudinale del punto da creare
     * @param  longitude Coordinata longitudinale del punto da creare
     */

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Restituisce la latitudine del punto geografico
     *
     * @return Coordinata latitudinale
     */

    public double getLatitude() {
        return latitude;
    }

    /**
     * Restituisce la longitudine del punto geografico
     *
     * @return Coordinata longitudinale
     */

    public double getLongitude() {
        return longitude;
    }
}
