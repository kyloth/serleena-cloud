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
 * Name: EmergencyContactDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.IEmergencyContactDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza IEmergencyContactDao per database MySQL utilizzando JDBC.
 *
 * @field tpl : JdbcTemplate Template JDBC per la connessione alla base di dati.
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class EmergencyContactDao implements IEmergencyContactDao {

    
    /**
     * Template JDBC per la connessione alla base di dati.
     */

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo EmergencyContactDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    EmergencyContactDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    /**
     * Classe che concretizza IEmergencyContactDao.findAll(Rect).
     *
     * @param region La regione di mappa di interesse.
     * @return Restituisce la lista dei contatti di emergenza relativi alla regione specificata.
     */

    public Iterable<EmergencyContact> findAll(Rect region) {
        return tpl.query("SELECT NWLatitude, NWLongitude, SELatitude, SELongitude, Name, Number " +
                         "FROM EmergencyContacts " +
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
        new RowMapper<EmergencyContact>() {
            @Override
            public EmergencyContact mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new EmergencyContact(rs.getString("Name"),
                                            new Rect(new Point(rs.getDouble("NWLatitude"),
                                                     rs.getDouble("NWLongitude")),
                                                     new Point(rs.getDouble("SELatitude"),
                                                             rs.getDouble("SELongitude"))),
                                            rs.getString("Number"));
            }
        });
    }
}
