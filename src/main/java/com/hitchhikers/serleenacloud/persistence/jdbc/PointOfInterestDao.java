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

import com.kyloth.serleenacloud.datamodel.business.PointOfInterest;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.persistence.IPointOfInterestDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PointOfInterestDao implements IPointOfInterestDao {

    private JdbcTemplate tpl;

    PointOfInterestDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public Iterable<PointOfInterest> findAll(IRect region) {
        return tpl.query("SELECT latitude, longitude, name, type " +
                         "FROM pois " +
                         "WHERE (latitude BETWEEN ? AND ?) AND (longitude BETWEEN ? AND ?) ",
                         new Object[] {
                             region.getNWPoint().getLatitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()},
                         new RowMapper<PointOfInterest>() {
                             @Override
                             public PointOfInterest mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 return new PointOfInterest(rs.getDouble("latitude"),
                                                            rs.getDouble("longitude"),
                                                            rs.getString("name"),
                                                            PointOfInterest.POIType.valueOf(rs.getString("type")));
                             }
                         });
    }
}
