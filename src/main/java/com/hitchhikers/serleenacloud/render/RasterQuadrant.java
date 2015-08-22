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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe per la gestione di un raster rappresentante una porzione di mappa.
 *
 * @use Ogni quadrante ritornato contiene un puntatore ai quattro quadranti adiacenti (o null) e all'oggetto di tipo Image che rappresenta una porzione di mappa renderizzata.
 *
 * @field ir : ImageRenderer Campo dati contenente l'oggetto rappresentante il rendering dell'Esperienza
 * @field x : int Campo dati rappresentante la coordinata x in pixel dell'angolo nord-ovest del quadrante
 * @field y : int Campo dati rappresentante la coordinata y in pixel dell'angolo nord-ovest del quadrante
 * @field quadrantHeight : int Campo dati statico contentente l'altezza in pixel di un quadrante
 * @field quadrantWidth : int Campo dati statico contentente la larghezza in pixel di un quadrante
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class RasterQuadrant {

    /**
     * Oggetto rappresentante il rendering dell'Esperienza.
     */

    ImageRenderer ir;

    /**
     * Coordinata x in pixel dell'angolo nord-ovest del quadrante.
     */

    int x;

    /**
     * Coordinata y in pixel dell'angolo nord-ovest del quadrante.
     */

    int y;

    /**
     * Altezza in pixel di un quadrante.
     */

    static int quadrantHeight;

    /**
     * Larghezza in pixel di un quadrante.
     */

    static int quadrantWidth;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

        quadrantWidth = (Integer)context.getBean("quadrantWidth");
        quadrantHeight = (Integer)context.getBean("quadrantHeight");
    }

    /**
     * Crea un nuovo RasterQuadrant.
     *
     * @param r ImageRenderer relativo all'Esperienza.
     */

    RasterQuadrant(ImageRenderer ir) {
        this.ir = ir;
        this.x = 0;
        this.y = quadrantHeight;
    }

    /**
     * Crea un nuovo RasterQuadrant.
     *
     * @param r ImageRenderer relativo all'Esperienza.
     * @param x coordinata x in pixel dell'angolo nord-ovest del quadrante
     * @param y coordinata y in pixel dell'angolo nord-ovest del quadrante
     */

    RasterQuadrant(ImageRenderer ir, int x, int y) {
        this.ir = ir;
        this.x = x;
        this.y = y;
    }

    /**
     * Restituisce la regione di mappa relativa al quadrante.
     *
     * @return Restituisce un oggetto di tipo Rect rappresentante la regione di mappa relativa al quadrante.
     */

    public Rect getBoundingRect() {

        int _x = x + Utils.round(ir.normalizeLongitude(ir.r.rect.getNWPoint()));
        int _y = y + Utils.round(ir.normalizeLatitude(ir.r.rect.getSEPoint()));

        Point nw = new Point(ir.YtoLat(_y),
                             ir.XtoLon(_x));
        Point se = new Point(ir.YtoLat(_y-quadrantHeight),
                             ir.XtoLon(_x+quadrantWidth));

        return new Rect(nw, se);
    }

    /**
     * Restituisce il quadrante a sud.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante a sud.
     */

    @JsonIgnore
    public RasterQuadrant getSouth() {
        int newY = y-quadrantWidth;
        return newY <= 0 ? null : new RasterQuadrant(ir, x, newY);
    }

    /**
     * Restituisce il quadrante a nord.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante a nord.
     */

    @JsonIgnore
    public RasterQuadrant getNorth() {
        int newY = y+quadrantHeight;
        return newY > ir.getImage().getHeight() ? null : new RasterQuadrant(ir, x, newY);
    }

    /**
     * Restituisce il quadrante ad est.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante ad est.
     */

    @JsonIgnore
    public RasterQuadrant getEast() {
        int newX = x+quadrantWidth;
        return newX >= ir.getImage().getWidth() ? null : new RasterQuadrant(ir, newX, y);
    }

    /**
     * Restituisce il quadrante ad ovest.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante ad ovest.
     */

    @JsonIgnore
    public RasterQuadrant getWest() {
        int newX = x-quadrantWidth;
        return newX < 0 ? null : new RasterQuadrant(ir, newX, y);
    }

    /**
     * Restituisce la porzione di mappa renderizzata relativa al quadrante.
     *
     * @return Restituisce una String rappresentante l'encoding base64 di un'immagine PNG rappresentante la porzione di mappa relativa al quadrante.
     */

    public String getImage() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(currentQuadrant(), "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Restituisce la porzione di mappa renderizzata relativa al quadrante.
     *
     * @return Restituisce una BufferedImage rappresentante la porzione di mappa relativa al quadrante.
     */

    BufferedImage currentQuadrant() {
        return ir.getImage().getSubimage(ir.getImage().getMinX()+ x,
                                         ir.getImage().getHeight()-y,
                                         quadrantWidth,
                                         quadrantHeight);
    }

}
