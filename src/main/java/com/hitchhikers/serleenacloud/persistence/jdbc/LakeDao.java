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
 * Name: LakeDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.Lake;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.ILakeDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza ILakeDao per database MySQL utilizzando JDBC.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class LakeDao implements ILakeDao {

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo LakeDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    LakeDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Metodo che implementa ILakeDao.findAll(Rect).
     *
     * @param region La regione di interesse.
     * @return Restituisce la lista dei laghi relativi alla regione specificata.
     */

    public Iterable<Lake> findAll(Rect region) {
        return tpl.query("SELECT DISTINCT LakeName " +
                         "FROM LakePoints " +
                         "WHERE (Latitude BETWEEN ? AND ?) AND (Longitude BETWEEN ? AND ?) ",
                         new Object[] {
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()
                         },
        new RowMapper<Lake>() {
            @Override
            public Lake mapRow(ResultSet rs, int rowNum) throws SQLException {
                String lakeName = rs.getString("LakeName");
                Iterable<Point> points = tpl.query("SELECT Latitude, Longitude " +
                                                    "FROM LakePoints " +
                                                    "WHERE LakeName = ? "+
                                                    "ORDER BY Idx",
                                                    new Object[] {lakeName},
                new RowMapper<Point>() {
                    @Override
                    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Point(rs.getDouble("Latitude"), rs.getDouble("Longitude"));
                    }
                });
                return new Lake(points, lakeName);
            }
        });
    }
}
