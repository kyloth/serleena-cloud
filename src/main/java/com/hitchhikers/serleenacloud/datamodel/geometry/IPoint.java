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
 * Name: IPoint.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

/**
 * Interfaccia per oggetti rappresentanti un punto geografico
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IPoint {


    /**
     * Restituisce la latitudine del punto geografico
     *
     * @return Coordinata latitudinale
     */

    public double getLatitude();

    /**
     * Restituisce la longitudine del punto geografico
     *
     * @return Coordinata longitudinale
     */

    public double getLongitude();
}
