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

import com.kyloth.serleenacloud.datamodel.business.Lake;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.geometry.IPoint;
import com.kyloth.serleenacloud.datamodel.geometry.Point;
import com.kyloth.serleenacloud.persistence.ILakeDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LakeDao implements ILakeDao {

    private JdbcTemplate tpl;

    LakeDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public Iterable<Lake> findAll(IRect region) {
        return tpl.query("SELECT DISTINCT LakeName " +
                         "FROM LakePoints " +
                         "WHERE (Latitude BETWEEN ? AND ?) AND (Longitude BETWEEN ? AND ?) ",
                         new Object[] {
                             region.getNWPoint().getLatitude(),
                             region.getSEPoint().getLatitude(),
                             region.getNWPoint().getLongitude(),
                             region.getSEPoint().getLongitude()},
                         new RowMapper<Lake>() {
                             @Override
                             public Lake mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 String lakeName = rs.getString("LakeName");
                                 Iterable<IPoint> points = tpl.query("SELECT Latitude, Longitude" +
                                                                     "FROM LakePoints" +
                                                                     "WHERE LakeName = ? "+
                                                                     "ORDER BY Idx",
                                                                     new Object[] {lakeName},
                                                                     new RowMapper<IPoint>() {
                                                                         @Override
                                                                         public IPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                                                                             return new Point(rs.getDouble("Latitude"), rs.getDouble("Longitude"));
                                                                         }
                                                                     });
                                 return new Lake(points, lakeName);
                             }
                         });
    }
}
