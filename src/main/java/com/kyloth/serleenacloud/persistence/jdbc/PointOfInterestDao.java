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
 * Name: PointOfInterestDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.persistence.IPointOfInterestDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza IPointOfInterestDao per database MySQL utilizzando JDBC.
 *
 * @field tpl : JdbcTemplate Template JDBC per la connessione alla base di dati
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class PointOfInterestDao implements IPointOfInterestDao {

    /**
     * Template JDBC per la connessione alla base di dati.
     */

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo PointOfInterestDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    PointOfInterestDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Metodo che implementa IPointOfInterestDao.findAll(Rect).
     *
     * @param region La regione di interesse.
     * @return Restituisce la lista dei punti di interesse relativi alla regione specificata.
     */

    public Iterable<PointOfInterest> findAll(Rect region) {
        return tpl.query("SELECT Latitude, Longitude, Name, Type " +
                         "FROM POIs " +
                         "WHERE (Latitude BETWEEN ? AND ?) AND (Longitude BETWEEN ? AND ?) ",
                         new Object[] {
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()
                         },
        new RowMapper<PointOfInterest>() {
            @Override
            public PointOfInterest mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PointOfInterest(rs.getDouble("Latitude"),
                                           rs.getDouble("Longitude"),
                                           rs.getString("Name"),
                                           PointOfInterest.POIType.valueOf(rs.getString("Type")));
            }
        });
    }
}
