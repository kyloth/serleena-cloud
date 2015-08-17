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
 * Name: Utils.java
 * Package: com.kyloth.serleenacloud.renderer
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.render;

/**
 * Classe di utilità generale per il package render.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

class Utils {

    static double projLatitude(double lat) {
        return Math.toDegrees(Math.log(Math.tan(Math.PI/4+Math.toRadians(lat)/2)));
    }

    static double projY(double x) {
        return Math.toDegrees(2* Math.atan(Math.exp(Math.toRadians(x))) - Math.PI/2);
    }

    static int round (double x) {
        return (int) Math.round(x);
    }

    static double multipleOf(double a, double b) {
        return Math.ceil(a/b)*b;
    }

}
