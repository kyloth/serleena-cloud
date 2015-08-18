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
 * Name: ImageRenderer.java
 * Package: com.kyloth.serleenacloud.render
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.render;

import com.kyloth.serleenacloud.datamodel.business.River;
import com.kyloth.serleenacloud.datamodel.business.Path;
import com.kyloth.serleenacloud.datamodel.business.Lake;
import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.business.UserPoint;
import com.kyloth.serleenacloud.datamodel.business.Track;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.ElevationRect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;

/**
 * Classe che si occupa del rendering effettivo degli elementi di un'Esperienza in un'immagine
 *
 * @use Prendendo in input un oggetto Renderer,
 * @field img : BufferedImage Campo dati contenente l'immagine generata
 * @field g : Graphics2d Campo dati rappresentante l'oggetto su cui disegnare l'immagine
 * @field width : int Campo dati rappresentante la larghezza totale dell'immagine in pixel
 * @field height : int Campo dati rappresentante l'altezza totale dell'immagine in pixel
 * @field maxLatitude : double Campo dati rappresentante la latitudine massima tra le entità dell'Esperienza
 * @field maxLongitude : double Campo dati rappresentante la longitudine massima tra le entità dell'Esperienza
 * @field minLatitude : double Campo dati rappresentante la latitudine minima tra le entità dell'Esperienza
 * @field minLongitude : double Campo dati rappresentante la longitudine minima tra le entità dell'Esperienza
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class ImageRenderer {

    /**
     * Oggetto da cui ottenere le informazioni sulle entità dell'Esperienza da renderizzare.
     */

    Renderer r;

    /**
     * Immagine generata.
     */

    BufferedImage img;

    /**
     * Oggetto su cui disegnare l'immagine.
     */

    Graphics2D g;

    /**
     * Larghezza totale dell'immagine in pixel.
     */

    double width;

    /**
     * Altezza totale dell'immagine in pixel.
     */

    double height;


    /**
     * Latitudine massima tra le entità dell'Esperienza.
     */

    double maxLatitude = Double.MIN_VALUE;

    /**
     * Longitudine massima tra le entità dell'Esperienza.
     */

    double maxLongitude = Double.MIN_VALUE;

    /**
     * Latitudine minima tra le entità dell'Esperienza.
     */

    double minLatitude = Double.MAX_VALUE;

    /**
     * Longitudine minima tra le entità dell'Esperienza.
     */

    double minLongitude = Double.MAX_VALUE;

    /**
     * Costruisce un nuovo ImageRenderer a partire dal Renderer
     *
     * @param r Renderer a partire dalla quale costruire l'ImageRenderer
     */

    ImageRenderer(Renderer r) {

        this.r = r;

        calcMaxLatLong();

        width = normalizeLongitude(maxLongitude);
        height = normalizeLatitude(maxLatitude);

        img = new BufferedImage(Utils.round(width), Utils.round(height), BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();

        draw();
    }

    /**
     * Disegna le entità.
     */

    void draw() {
        g.setBackground(Utils.backgroundColor);
        g.clearRect(0, 0, Utils.round(width), Utils.round(height));

        for (ElevationRect er : r.elevations)
            drawElevation(er);

        for (Lake t : r.lakes)
            drawLake(t);

        for (River t : r.rivers)
            drawRiver(t);

        for (Path t : r.paths)
            drawPath(t);

        for (Track t : r.tracks)
            drawTrack(t);

        for (PointOfInterest p : r.pois)
            drawPOI(p);

        for (UserPoint p : r.ups)
            drawUP(p);

    }

    /**
     * Disegna le informazioni di altitudine.
     *
     * @param er ElevationRect da disegnare.
     */

    void drawElevation(ElevationRect er) {
        Color c = Utils.elevationColor;
        for (int i = 0; i < er.getHeight(); i++)
            c = c.darker();
        g.setColor(c);

        double nwLat = Math.min(er.getNWPoint().getLatitude(), maxLatitude);
        double nwLon = Math.max(er.getNWPoint().getLongitude(), minLongitude);

        double seLat = Math.max(er.getSEPoint().getLatitude(), minLatitude);
        double seLon = Math.min(er.getSEPoint().getLongitude(), maxLongitude);

        g.fillRect(Utils.round(normalizeLongitude(nwLon)),
                   Utils.round(height-normalizeLatitude(nwLat)),
                   Utils.round(normalizeLongitude(seLon))-Utils.round(normalizeLongitude(nwLon)),
                   Utils.round(normalizeLatitude(nwLat))-Utils.round(normalizeLatitude(seLat)));
    }

    /**
     * Individua le latitudini e longitudini massime e minime tra le entità da disegnare.
     */

    void calcMaxLatLong() {

        calcMaxLatLong(r.rect.getPoints());

        calcMaxLatLong(r.pois);
        calcMaxLatLong(r.ups);

        for (Track t : r.tracks)
            calcMaxLatLong(t.getCheckPoints());
        for (Path t : r.paths)
            calcMaxLatLong(t.getPoints());
        for (River t : r.rivers)
            calcMaxLatLong(t.getPoints());
        for (Lake t : r.lakes)
            calcMaxLatLong(t.getPoints());

    }

    /**
     * Individua le latitudini e longitudini massime e minime nella lista dei punti.
     *
     * @param points Punti su cui individuare latitudine e longitudine massima e minima.
     */

    void calcMaxLatLong(Iterable<? extends Point> points) {
        for (Point p : points) {
            double lat = p.getLatitude();
            double lon = p.getLongitude();

            if (lat > maxLatitude) {
                maxLatitude = lat;
            } else if (lat < minLatitude) {
                minLatitude = lat;
            }

            if (lon > maxLongitude) {
                maxLongitude = lon;
            } else if (lon < minLongitude) {
                minLongitude = lon;
            }
        }
    }

    /**
     * Disegna un icona nell'immagine..
     *
     * @param i L'icona da disegnare.
     * @param x La coordinata x
     * @param x La coordinata y
     */

    void drawImage(BufferedImage i, int x, int y) {
        int off = (int)i.getHeight()/2;
        x = x-off;
        y = y-off;
        x = x < off ? off :x;
        y = x < off ? off :y;
        g.drawImage(i, x, y, null);
    }

    /**
     * Disegna un punto d'interesse.
     *
     * @param p Punto d'interesse da disegnare.
     */

    void drawPOI(PointOfInterest p) {
        BufferedImage i = null;
        switch (p.getPOIType()) {
        case FOOD:
            i = Utils.food;
            break;
        case INFO:
            i = Utils.info;
            break;
        case WARNING:
            i = Utils.warning;
            break;
        }
        drawImage(i, Utils.round(normalizeLongitude(p)), Utils.round(height-normalizeLatitude(p)));
    }

    /**
     * Disegna un punto utente.
     *
     * @param p Punto utente da disegnare.
     */

    void drawUP(UserPoint p) {
        drawImage(Utils.up, Utils.round(normalizeLongitude(p)), Utils.round(height-normalizeLatitude(p)));
    }

    /**
     * Disegna un lago.
     *
     * @param l Lago da disegnare.
     */

    void drawLake(Lake l) {
        drawPoly(Utils.lakeColor, l.getPoints());
    }

    /**
     * Disegna un poligono riempito di un colore.
     *
     * @param color Colore di cui riempire il poligono.
     * @param points Insieme di punti individuanti il poligono.
     */

    void drawPoly(Color color, Iterable<Point> points) {
        int size = 0;
        for (Point p : points) {
            size++;
        }

        int[] x = new int[size];
        int[] y = new int[size];

        g.setColor(color);
        int i = 0;
        for (Point p : points) {
            x[i] = Utils.round(normalizeLongitude(p));
            y[i] = Utils.round(height-normalizeLatitude(p));
        }

        g.fillPolygon(x, y, size);
    }

    /**
     * Disegna un sentiero.
     *
     * @param p Sentiero da disegnare.
     */

    void drawPath(Path p) {
        ArrayList<Point> points = new ArrayList<Point>();
        for (Point point : p.getPoints()) {
            points.add(point);
        }
        drawPoly(Utils.pathColor, points);
    }

    /**
     * Disegna un fiume.
     *
     * @param r Fiume da disegnare.
     */

    void drawRiver(River r) {
        ArrayList<Point> points = new ArrayList<Point>();
        for (Point point : r.getPoints()) {
            points.add(point);
        }

        drawPoly(Utils.riverColor, points);
    }

    /**
     * Disegna un percorso.
     *
     * @param t Percorso da disegnare.
     */

    void drawTrack(Track t) {
        int size = 0;
        for (Point p : t.getCheckPoints()) {
            size++;
        }

        int[] x = new int[size];
        int[] y = new int[size];

        g.setColor(Utils.checkPointColor);
        int i = 0;
        for (Point p : t.getCheckPoints()) {
            x[i] = Utils.round(normalizeLongitude(p));
            y[i] = Utils.round(height-normalizeLatitude(p));
            i++;
        }

        g.setColor(Utils.trackLineColor);
        g.drawPolyline(x, y, size);

        i = 0;
        while (i < size) {
            drawImage(Utils.cp, x[i], y[i]);
            i++;
        }
    }

    /**
     * Dato un punto con coordinate in gradi, ritorna la sua latitudine in pixel.
     *
     * @param p Punto di cui interessa la latitudine
     * @return Restituisce la latitudine in pixel
     */

    double normalizeLatitude(Point p) {
        return normalizeLatitude(p.getLatitude());
    }

    /**
     * Data una coordinata latitudinale in gradi, ritorna la sua proiezione in pixel.
     *
     * @param lat Coordinata latitudinaale in gradi
     * @return Restituisce la proiezione della latitudine in pixel
     */

    double normalizeLatitude(double lat) {
        return (Utils.projLatitude(lat)-Utils.projLatitude(minLatitude))*Utils.factor/180;
    }

    /**
     * Dato un punto con coordinate in gradi, ritorna la sua longitudine in pixel.
     *
     * @param p Punto di cui interessa la longitudine
     * @return Restituisce la longitudine in pixel
     */

    double normalizeLongitude(Point p) {
        return normalizeLongitude(p.getLongitude());
    }

    /**
     * Data una coordinata longitudinale in gradi, ritorna la sua proiezione in pixel.
     *
     * @param lon Coordinata longitudinaale in gradi
     * @return Restituisce la proiezione della longitudine in pixel
     */

    double normalizeLongitude(double lon) {
        // lonUtils.Factor == 100 -> 1 grado per 100 pixel
        return ((lon-minLongitude)*Utils.factor)/180;
    }

    /**
     * Data una proiezione latitudinale in pixel, ritorna la sua coordinata approssimativa in gradi.
     *
     * @param y Proiezione latitudinale in pixel
     * @return Restituisce la coordinata latitudinale in gradi corrispondente alla proiezione in pixel
     */

    double YtoLat(double y) {
        return Utils.projY(y*180/Utils.factor+Utils.projLatitude(minLatitude));
    }


    /**
     * Data una proiezione longitudinale in pixel, ritorna la sua coordinata approssimativa in gradi.
     *
     * @param x Proiezione longitudinale in pixel
     * @return Restituisce la coordinata longitudinale in gradi corrispondente alla proiezione in pixel
     */

    double XtoLon(double x) {
        return x*360/Utils.factor+minLongitude;
    }


    /**
     * Restituisce l'immagine renderizzata, con dimensioni multiple di Utils.quadrantHeight e Utils.quadrantWidth
     *
     * @return Restituisce l'immagine renderizzata
     */

    BufferedImage getImage() {

        double nwHeight = normalizeLatitude(r.rect.getNWPoint());
        double nwWidth = normalizeLongitude(r.rect.getNWPoint());

        double seHeight = normalizeLatitude(r.rect.getSEPoint());
        double seWidth = normalizeLongitude(r.rect.getSEPoint());

        int width = Utils.round(seWidth-nwWidth);
        int height = Utils.round(nwHeight-seHeight);

        if (img.getWidth() != width || img.getHeight() != height)
            img = img.getSubimage(Utils.round(nwWidth), Utils.round(img.getHeight()-nwHeight), width, height);

        int mWidth = Utils.round(Utils.multipleOf(width, RasterQuadrant.quadrantWidth));
        int mHeight = Utils.round(Utils.multipleOf(height, RasterQuadrant.quadrantHeight));

        if (mWidth != width || mHeight != height) {
            BufferedImage _img = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
            _img.createGraphics().drawImage(img, 0, mHeight-height, null);
            img = _img;
        }
        return img;
    }
}
