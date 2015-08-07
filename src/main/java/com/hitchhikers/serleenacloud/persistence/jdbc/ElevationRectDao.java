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
 * Name: ElevationRectDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.geometry.ElevationRect;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.IElevationRectDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza IElevationRectDao per database MySQL utilizzando JDBC.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class ElevationRectDao implements IElevationRectDao {

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo ElevationRectDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    ElevationRectDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Metodo che implementa IElevationRectDao.findAll(Rect).
     *
     * @param region La regione di interesse.
     * @return Restituisce la lista dei rettangoli di elevazione relativi alla regione specificata.
     */
    public Iterable<ElevationRect> findAll(Rect region) {
        return tpl.query("SELECT NWLatitude, NWLongitude, SELatitude, SELongitude, Height " +
                         "FROM ElevationRect " +
                         "WHERE ((NWLatitude BETWEEN ? AND ?) AND (NWLongitude BETWEEN ? AND ?)) " +
                         "OR ((SELatitude BETWEEN ? AND ?) AND (SELongitude BETWEEN ? AND ?)) " +
                         "OR ((NWLongitude BETWEEN ? AND ?) AND (SELatitude BETWEEN ? AND ?)) " +
                         "OR ((NWLatitude BETWEEN ? AND ?) AND (SELongitude BETWEEN ? AND ?)) ",
                         new Object[] {
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()
                         },
        new RowMapper<ElevationRect>() {
            @Override
            public ElevationRect mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ElevationRect(new Point(rs.getDouble("NWLatitude"),
                                                   rs.getDouble("NWLongitude")),
                                         new Point(rs.getDouble("SELatitude"),
                                                   rs.getDouble("SELongitude")),
                                         rs.getInt("Height"));
            }
        });
    }
}
