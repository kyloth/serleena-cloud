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
 * Name: Lake.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.Point;

import java.util.Arrays;

/**
 * Classe che rappresenta un'entità di mappa di tipo lago.
 *
 * @use Viene utilizzata da Render.Renderer durante la creazione dei quadranti raster
 * @field points : Iterable<Point> Insieme dei punti che delimitano il perimetro dell'area poligonale del lago
 * @field name : String Nome del lago
 * 
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Lake {

    /**
     * Insieme dei punti che delimitano il perimetro dell'area poligonale del lago.
     */

    private Iterable<Point> points;

    /**
     * Nome del lago.
     */

    private String name;

    /**
     * Crea un nuovo oggetto Lake inizializzando i suoi campi dati.
     *
     * @param points Insieme dei punti che delimitano il perimetro del lago.
     * @param name Nome del lago.
     */

    public Lake(Iterable<Point> points, String name) {
        this.points = points;
        this.name = name;
    }

    /**
     * Crea un nuovo oggetto Lake inizializzando i suoi campi dati.
     *
     * @param points Array dei punti che delimitano il perimetro del lago.
     * @param name Nome del lago.
     */

    public Lake(Point[] points, String name) {
        this.points = Arrays.asList(points);
        this.name = name;
    }

    /**
     * Metodo getter che permette di ottenere il nome del lago.
     *
     * @return Restituisce il nome del lago.
     */

    public String getName() {
        return name;
    }

    /**
     * Metodo getter che permette di ottenere l'insieme dei punti che delimitano il perimetro del lago.
     *
     * @return Restituisce l'insieme dei punti che delimitano il perimetro del lago.
     */

    public Iterable<Point> getPoints() {
        return points;
    }
}
