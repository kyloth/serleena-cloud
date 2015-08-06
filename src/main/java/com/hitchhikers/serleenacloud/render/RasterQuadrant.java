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
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class RasterQuadrant {

    ImageRenderer ir;
    BufferedImage img;

    int x;
    int y;

    static ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    static int quadrantHeight = (Integer)context.getBean("quadrantHeight");
    static int quadrantWidth = (Integer)context.getBean("quadrantWidth");

    /**
     * Crea un nuovo RasterQuadrant.
     *
     * @param r Il Renderer relativo a questo quadrante.
     */

    RasterQuadrant(ImageRenderer ir) {
        this.ir = ir;
        this.img = ir.getImage();
        this.x = 0;
        this.y = quadrantHeight;
    }

    RasterQuadrant(ImageRenderer ir, int x, int y) {
        this.ir = ir;
        this.img = ir.getImage();
        this.x = x;
        this.y = y;
    }

    /**
     * Restituisce la regione di mappa relativa al quadrante.
     *
     * @return Restituisce un oggetto di ripo Rect rappresentante la regione di mappa relativa al quadrante.
     */

    public Rect getBoundingRect() {

        Point nw = new Point(ir.YtoLat(img.getMinY()+y),
                             ir.XtoLon(img.getMinX()+x));
        Point se = new Point(ir.YtoLat(img.getMinY()+(y-quadrantHeight)),
                             ir.XtoLon(img.getMinX()+(x+quadrantWidth)));

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
        return newY > img.getHeight() ? null : new RasterQuadrant(ir, x, newY);
    }

    /**
     * Restituisce il quadrante ad est.
     *
     * @return Restituisce un oggetto RasterQuadrant rappresentante il quadrante ad est.
     */

    @JsonIgnore
    public RasterQuadrant getEast() {
        int newX = x+quadrantWidth;
        return newX >= img.getWidth() ? null : new RasterQuadrant(ir, newX, y);
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
     * @return Restituisce un oggetto di tipo Image rappresentante la porzione di mappa relativa al quadrante.
     */

    public String getImage() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(currentQuadrant(), "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    BufferedImage currentQuadrant() {
        return img.getSubimage(img.getMinX()+ x, img.getHeight()-y, quadrantWidth, quadrantHeight);
    }

}
