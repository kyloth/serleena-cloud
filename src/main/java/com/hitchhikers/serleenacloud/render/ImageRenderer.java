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

    Renderer r;

    /**
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

    static Color colorFromString(String s) {
        try {
            return (Color)Color.class.getField(s).get(null);
        } catch (Exception e) {
            throw new RuntimeException("color not found");
        }
    }

    ImageRenderer(Renderer r) {

        this.r = r;

        calcMaxLatLong();

        width = normalizeLongitude(maxLongitude);
        height = normalizeLatitude(maxLatitude);

        img = new BufferedImage(Utils.round(width), Utils.round(height), BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();

        draw();
    }

    void draw() {
        g.setBackground(backgroundColor);
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

    void drawElevation(ElevationRect er) {
        Color c = elevationColor;
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
        g.drawImage(i, Utils.round(normalizeLongitude(p)), Utils.round(height-normalizeLatitude(p)), null);
    }

    void drawUP(UserPoint p) {
        g.drawImage(up, Utils.round(normalizeLongitude(p)), Utils.round(height-normalizeLatitude(p)), null);
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
            x[i] = Utils.round(normalizeLongitude(p));
            y[i] = Utils.round(height-normalizeLatitude(p));
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
            x[i] = Utils.round(normalizeLongitude(p));
            y[i] = Utils.round(height-normalizeLatitude(p));
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

    double normalizeLatitude(double lat) {
        return (Utils.projLatitude(lat)-Utils.projLatitude(minLatitude))*factor/180;
    }

    double normalizeLongitude(Point p) {
        return normalizeLongitude(p.getLongitude());
    }

    double normalizeLongitude(double lon) {
        // lonFactor == 100 -> 1 grado per 100 pixel
        return ((lon-minLongitude)*factor)/360;
    }

    double YtoLat(double x) {
        return Utils.projY(x*180/factor+Utils.projLatitude(minLatitude));
    }
    double XtoLon(double x) {
        return x*360/factor+minLongitude;
    }

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
