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
 * Name: PathDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.Path;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.IPathDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza IPathDao per database MySQL utilizzando JDBC.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class PathDao implements IPathDao {

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo PathDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    PathDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Metodo che implementa IPathDao.findAll(Rect).
     *
     * @param region La regione di interesse.
     * @return Restituisce tutti i percorsi relativi alla regione specificata.
     */

    public Iterable<Path> findAll(Rect region) {
        return tpl.query("SELECT DISTINCT PathName " +
                         "FROM PathPoints " +
                         "WHERE (Latitude BETWEEN ? AND ?) AND (Longitude BETWEEN ? AND ?) ",
                         new Object[] {
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()
                         },
        new RowMapper<Path>() {
            @Override
            public Path mapRow(ResultSet rs, int rowNum) throws SQLException {
                String pathName = rs.getString("PathName");
                Iterable<Point> points = tpl.query("SELECT Latitude, Longitude " +
                                                   "FROM PathPoints " +
                                                   "WHERE PathName = ? "+
                                                   "ORDER BY Idx",
                                                 new Object[] {pathName},
                new RowMapper<Point>() {
                    @Override
                    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Point(rs.getDouble("Latitude"), rs.getDouble("Longitude"));
                    }
                });
                return new Path(points, pathName);
            }
        });
    }
}
