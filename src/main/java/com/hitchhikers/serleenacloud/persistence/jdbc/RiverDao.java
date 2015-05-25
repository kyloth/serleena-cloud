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
 * Name: RiverDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.River;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.WeighedPoint;
import com.kyloth.serleenacloud.persistence.IRiverDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza IRiverDao per database MySQL utilizzando JDBC.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class RiverDao implements IRiverDao {

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo RiverDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    RiverDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Metodo che implementa IRiverDao.findAll(Rect).
     *
     * @param region La regione di interesse.
     * @return Restituisce la lista dei fiumi relativi alla regione specificata.
     */

    public Iterable<River> findAll(Rect region) {
        return tpl.query("SELECT DISTINCT RiverName " +
                         "FROM RiverPoints " +
                         "WHERE (Latitude BETWEEN ? AND ?) AND (Longitude BETWEEN ? AND ?) ",
                         new Object[] {
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()
                         },
        new RowMapper<River>() {
            @Override
            public River mapRow(ResultSet rs, int rowNum) throws SQLException {
                String riverName = rs.getString("RiverName");
                Iterable<WeighedPoint> points = tpl.query("SELECT Latitude, Longitude, Radius " +
                                                 "FROM RiverPoints " +
                                                 "WHERE RiverName = ? "+
                                                 "ORDER BY Idx",
                                                 new Object[] {riverName},
                new RowMapper<WeighedPoint>() {
                    @Override
                    public WeighedPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new WeighedPoint(rs.getDouble("Latitude"), rs.getDouble("Longitude"),
                                                rs.getDouble("Radius"));
                    }
                });
                return new River(points, riverName);
            }
        });
    }
}
