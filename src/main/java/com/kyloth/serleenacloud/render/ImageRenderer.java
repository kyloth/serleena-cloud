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


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.net.URL;

import java.io.IOException;
import java.util.ArrayList;

public class ImageRenderer {

    Rect rect;
    Renderer renderer;

    static ApplicationContext context;

    static int quadrantHeight;
    static int quadrantWidth;

    static int factor;

    static Color trackLineColor;
    static Color checkPointColor;
    static Color lakeColor;
    static Color pathColor;
    static Color riverColor;
    static Color backgroundColor;
    static Color elevationColor;


    static BufferedImage up;
    static BufferedImage food;
    static BufferedImage info;
    static BufferedImage warning;

    static {
        context = new ClassPathXmlApplicationContext("Spring-Module.xml");

        quadrantHeight = (Integer)context.getBean("quadrantHeight");
        quadrantWidth = (Integer)context.getBean("quadrantWidth");

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

    BufferedImage img;
    Graphics2D g;

    double width;
    double height;

    double maxLatitude = Double.MIN_VALUE;
    double maxLongitude = Double.MIN_VALUE;
    double minLatitude = Double.MAX_VALUE;
    double minLongitude = Double.MAX_VALUE;

    static BufferedImage imageFromFile(String s) {
        try {
            return ImageIO.read(Thread.currentThread().getContextClassLoader().getResource(s));
        } catch (IOException e) {
            throw new RuntimeException("image not found");
        }
    }

    static Color colorFromString(String s) {
        try {
            return (Color)Color.class.getField(s).get(null);
        } catch (Exception e) {
            throw new RuntimeException("color not found");
        }
    }

    ImageRenderer(Renderer r) {

        renderer = r;
        rect = r.rect;

        calcMaxLatLong();

        width = normalizeLongitude(maxLongitude);
        height = normalizeLatitude(maxLatitude);

        double nwHeight = normalizeLatitude(rect.getNWPoint());
        double nwWidth = normalizeLongitude(rect.getNWPoint());

        double seHeight = normalizeLatitude(rect.getSEPoint());
        double seWidth = normalizeLongitude(rect.getSEPoint());

        width = Math.max(multipleOf(width, quadrantWidth),
                         nwWidth+multipleOf(seWidth-nwWidth, quadrantWidth));
        height = Math.max(multipleOf(height, quadrantHeight),
                          seHeight+multipleOf(nwHeight-seHeight, quadrantHeight));

        img = new BufferedImage(round(width), round(height), BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();

        draw();
    }

    int round (double x) {
        return (int) Math.round(x);
    }

    double multipleOf(double a, double b) {
        return Math.ceil(a/b)*b;
    }

    void draw() {
        g.setBackground(backgroundColor);
        g.clearRect(0, 0, round(width), round(height));

        drawElevations();
        drawLakes();
        drawPaths();
        drawRivers();
        drawTracks();
        drawPOIs();
        drawUPs();
    }


    void drawElevations() {
        for (ElevationRect r : renderer.elevations)
            drawElevation(r);
    }


    void drawElevation(ElevationRect r) {
        Color c = elevationColor;
        for (int i = 0; i < r.getHeight(); i++)
            c = c.darker();
        g.setColor(c);

        double nwLat = Math.min(rect.getNWPoint().getLatitude(), maxLatitude);
        double nwLon = Math.max(rect.getNWPoint().getLongitude(), minLongitude);

        double seLat = Math.max(rect.getSEPoint().getLatitude(), minLatitude);
        double seLon = Math.min(rect.getSEPoint().getLongitude(), maxLongitude);

        g.fillRect(round(normalizeLatitude(nwLat)),
                   round(normalizeLongitude(nwLon)),
                   round(normalizeLongitude(seLon))-round(normalizeLongitude(nwLon)),
                   round(normalizeLatitude(nwLat))-round(normalizeLatitude(seLat)));

    }


    void calcMaxLatLong() {

        calcMaxLatLong(rect.getPoints());

        calcMaxLatLong(renderer.pois);
        calcMaxLatLong(renderer.ups);

        for (Track t : renderer.tracks)
            calcMaxLatLong(t.getCheckPoints());
        for (Path t : renderer.paths)
            calcMaxLatLong(t.getPoints());
        for (River t : renderer.rivers)
            calcMaxLatLong(t.getPoints());
        for (Lake t : renderer.lakes)
            calcMaxLatLong(t.getPoints());

    }

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

    void drawPOIs() {
        for (PointOfInterest p : renderer.pois)
            drawPOI(p);
    }

    void drawUPs() {
        for (UserPoint p : renderer.ups)
            drawUP(p);
    }

    void drawTracks() {
        for (Track t : renderer.tracks)
            drawTrack(t);
    }

    void drawPaths() {
        for (Path t : renderer.paths)
            drawPath(t);
    }

    void drawRivers() {
        for (River t : renderer.rivers)
            drawRiver(t);
    }

    void drawLakes() {
        for (Lake t : renderer.lakes)
            drawLake(t);
    }

    void drawPOI(PointOfInterest p) {
        BufferedImage i = null;
        switch (p.getPOIType()) {
        case FOOD:
            i = food;
            break;
        case INFO:
            i = info;
            break;
        case WARNING:
            i = warning;
            break;
        }
        g.drawImage(i, round(normalizeLongitude(p)), round(height-normalizeLatitude(p)), null);
    }

    void drawUP(UserPoint p) {
        g.drawImage(up, round(normalizeLongitude(p)), round(height-normalizeLatitude(p)), null);
    }

    void drawLake(Lake l) {
        drawPoly(lakeColor, l.getPoints());
    }

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
            x[i] = round(normalizeLongitude(p));
            y[i] = round(height-normalizeLatitude(p));
        }

        g.fillPolygon(x, y, size);
    }

    void drawPath(Path p) {
        ArrayList<Point> points = new ArrayList<Point>();
        for (Point point : p.getPoints()) {
            points.add(point);
        }
        drawPoly(pathColor, points);
    }

    void drawRiver(River r) {
        ArrayList<Point> points = new ArrayList<Point>();
        for (Point point : r.getPoints()) {
            points.add(point);
        }

        drawPoly(riverColor, points);
    }

    void drawTrack(Track t) {
        int size = 0;
        for (Point p : t.getCheckPoints()) {
            size++;
        }

        int[] x = new int[size];
        int[] y = new int[size];

        g.setColor(checkPointColor);
        int i = 0;
        for (Point p : t.getCheckPoints()) {
            x[i] = round(normalizeLongitude(p));
            y[i] = round(height-normalizeLatitude(p));
        }

        g.setColor(trackLineColor);
        g.drawPolyline(x, y, size);

        i = 0;
        while (i < size) {
            img.setRGB(x[i], y[i], checkPointColor.getRGB());
            i++;
        }
    }

    double normalizeLatitude(Point p) {
        return normalizeLatitude(p.getLatitude());
    }

    double projLatitude(double lat) {
        return Math.toDegrees(Math.log(Math.tan(Math.PI/4+Math.toRadians(lat)/2)));
    }

    double normalizeLatitude(double lat) {
        return (projLatitude(lat)-projLatitude(minLatitude))*factor/180;
    }

    double normalizeLongitude(Point p) {
        return normalizeLongitude(p.getLongitude());
    }

    double normalizeLongitude(double lon) {
        // lonFactor == 100 -> 1 grado per 100 pixel
        return ((lon-minLongitude)*factor)/360;
    }

    double projY(double x) {
        return Math.toDegrees(2* Math.atan(Math.exp(Math.toRadians(x))) - Math.PI/2);
    }

    double YtoLat(double x) {
        return projY(x*180/factor+projLatitude(minLatitude));
    }
    double XtoLon(double x) {
        return x*360/factor+minLongitude;
    }

    public BufferedImage getImage() {

        double nwHeight = normalizeLatitude(rect.getNWPoint());
        double nwWidth = normalizeLongitude(rect.getNWPoint());

        double seHeight = normalizeLatitude(rect.getSEPoint());
        double seWidth = normalizeLongitude(rect.getSEPoint());

        int width = round(multipleOf(seWidth-nwWidth, quadrantWidth));
        int height = round(multipleOf(nwHeight-seHeight, quadrantHeight));

        if (img.getWidth() != width || img.getHeight() != height)
            img = img.getSubimage(round(nwWidth), round(img.getHeight()-nwHeight), width, height);
        return img;
    }
}
