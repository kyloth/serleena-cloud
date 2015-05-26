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
 * Name: RasterQuadrant.java
 * Package: com.kyloth.serleenacloud.render
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.render;

import com.kyloth.serleenacloud.datamodel.geometry.Rect;

import java.awt.Image;

/**
 * Classe per la gestione di un raster rappresentante una porzione di mappa.
 *
 * @use Ogni quadrante ritornato contiene un puntatore ai quattro quadranti adiacenti (o null) e all'oggetto di tipo Image che rappresenta una porzione di mappa renderizzata.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class RasterQuadrant {

    Rect rect;

    /**
     * Crea un nuovo RasterQuadrant.
     *
     * @param r Il Renderer relativo a questo quadrante.
     * @param x Longitudine del punto nell'angolo nord ovest del quadrante.
     * @param y Latitudine del punto nell'angolo nord ovest del quadrante.
     */

    RasterQuadrant(Renderer r, int x, int y) {

    }

    /**
     * Restituisce la regione di mappa relativa al quadrante.
     *
     * @return Restituisce un oggetto di ripo Rect rappresentante la regione di mappa relativa al quadrante.
     */

    public Rect getBoundingRect() {
        return rect;
    }

    /**
     * Restituisce il quadrante a nord.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante a nord.
     */

    public RasterQuadrant getNorth() {
        return null;
    }

    /**
     * Restituisce il quadrante a sud.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante a sud.
     */

    public RasterQuadrant getSouth() {
        return null;
    }

    /**
     * Restituisce il quadrante ad est.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante ad est.
     */

    public RasterQuadrant getEast() {
        return null;
    }

    /**
     * Restituisce il quadrante ad ovest.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante ad ovest.
     */

    public RasterQuadrant getWest() {
        return null;
    }

    /**
     * Restituisce la porzione di mappa renderizzata relativa al quadrante.
     *
     * @return Restituisce un oggetto di tipo Image rappresentante la porzione di mappa relativa al quadrante.
     */

    public Image getImage() {
        return null;
    }

}
