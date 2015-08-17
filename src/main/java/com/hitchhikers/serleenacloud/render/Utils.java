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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.IOException;

/**
 * Classe di utilità generale per il package render.
 *
 * @field factor : int Campo dati statico rappresentante il fattore moltiplicativo per la conversione da gradi a pixel
 * @field trackLineColor : Color Campo dati statico rappresentante il colore da utilizzare per i percorsi
 * @field checkPointColor : Color Campo dati statico rappresentante il colore da utilizzare per i checkpoint
 * @field lakeColor : Color Campo dati statico rappresentante il colore da utilizzare per i laghi
 * @field pathColor : Color Campo dati statico rappresentante il colore da utilizzare per i sentieri
 * @field riverColor : Color Campo dati statico rappresentante il colore da utilizzare per i fiumi
 * @field backgroundColor : Color Campo dati statico rappresentante il colore da utilizzare per lo sfondo
 * @field elevationColor : Color Campo dati statico rappresentante il colore base per i quadranti di altitudine
 * @field up : BufferedImage Campo dati statico contenente l'immagine da usare per un punto utente
 * @field food : BufferedImage Campo dati statico contenente l'immagine da usare per un punto d'interesse di tipo FOOD
 * @field info : BufferedImage Campo dati statico contenente l'immagine da usare per un punto d'interesse di tipo INFO
 * @field warning : BufferedImage Campo dati statico contenente l'immagine da usare per un punto d'interesse di tipo WARNING
 * @field r: Renderer Campo dati contenente un oggetto da cui ottenere le informazioni sulle entità dell'Esperienza da renderizzare
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

class Utils {

    /**
     * Fattore moltiplicativo per la conversione da gradi a pixel.
     */

    static int factor;

    /**
     * Colore da utilizzare per i percorsi.
     */

    static Color trackLineColor;

    /**
     * Colore da utilizzare per i checkpoint.
     */

    static Color checkPointColor;

    /**
     * Colore da utilizzare per i laghi.
     */

    static Color lakeColor;

    /**
     * Colore da utilizzare per i sentieri..
     */

    static Color pathColor;

    /**
     * Colore da utilizzare per i fiumi..
     */

    static Color riverColor;

    /**
     * Colore da utilizzare per lo sfondo..
     */

    static Color backgroundColor;

    /**
     * Colore base per i quadranti di altitudine.
     */

    static Color elevationColor;

    /**
     * Immagine da usare per un punto utente
     */

    static BufferedImage up;

    /**
     * Immagine da usare per un punto d'interesse di tipo FOOD.
     */

    static BufferedImage food;

    /**
     * Immagine da usare per un punto d'interesse di tipo INFO.
     */

    static BufferedImage info;

    /**
     * Immagine da usare per un punto d'interesse di tipo WARNING.
     */

    static BufferedImage warning;

    static {

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

        factor = (Integer)context.getBean("factor");

        trackLineColor = colorFromString((String)context.getBean("trackLineColor"));
        checkPointColor = colorFromString((String)context.getBean("checkPointColor"));
        lakeColor = colorFromString((String)context.getBean("lakeColor"));
        pathColor = colorFromString((String)context.getBean("pathColor"));
        riverColor = colorFromString((String)context.getBean("riverColor"));
        backgroundColor = colorFromString((String)context.getBean("backgroundColor"));
        elevationColor = colorFromString((String)context.getBean("elevationColor"));

        up = imageFromFile("up.png");
        food = imageFromFile("food.png");
        info = imageFromFile("info.png");
        warning = imageFromFile("warning.png");
    }

    /**
     * Resituisce un oggetto BufferedImage rappresentante l'immagine in classpath individuata dalla stringa.
     *
     * @param s Posizione in classpath dell'immagine
     * @return Restituisce un oggetto BufferedImage rappresentante l'immagine
     */

    static BufferedImage imageFromFile(String s) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL res = cl != null ? cl.getResource(s) : null;
            if (res == null) {
                cl = ImageRenderer.class.getClassLoader();
                if (cl != null)
                    res = cl.getResource(s);
            }
            return ImageIO.read(res);
        } catch (IOException e) {
            throw new RuntimeException("image not found");
        }
    }

    /**
     * Resituisce un oggetto Color a partire dal suo nome
     *
     * @param s Nome del colore
     * @return Restituisce l'oggetto Color richiesto
     */

    static Color colorFromString(String s) {
        try {
            return (Color)Color.class.getField(s).get(null);
        } catch (Exception e) {
            throw new RuntimeException("color not found");
        }
    }

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
