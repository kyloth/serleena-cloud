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


package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.IEmergencyContact;
import com.kyloth.serleenacloud.datamodel.business.EmergencyContact;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.IEmergencyContactDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmergencyContactDao implements IEmergencyContactDao {

    private JdbcTemplate tpl;

    EmergencyContactDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public Iterable<IEmergencyContact> findAll(IRect region) {
        return tpl.query("SELECT NWLatitude, NWLongitude, SELatitude, SELongitude, Name, Number " +
                         "FROM EmergencyContacts " +
                         "WHERE ((NWLatitude BETWEEN ? AND ?) AND (NWLongitude BETWEEN ? AND ?))" +
                         "OR ((SELatitude BETWEEN ? AND ?) AND (SELongitude BETWEEN ? AND ?))",
                         new Object[] {
                             region.getNWPoint().getLatitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude(),
                             region.getNWPoint().getLatitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()},
                         new RowMapper<IEmergencyContact>() {
                             @Override
                             public IEmergencyContact mapRow(ResultSet rs, int rowNum) throws SQLException {
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
