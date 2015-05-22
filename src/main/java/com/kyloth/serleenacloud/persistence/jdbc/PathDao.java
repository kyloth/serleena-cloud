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

import com.kyloth.serleenacloud.datamodel.business.Path;
import com.kyloth.serleenacloud.datamodel.geometry.IRect;
import com.kyloth.serleenacloud.datamodel.geometry.IWeighedPoint;
import com.kyloth.serleenacloud.datamodel.geometry.WeighedPoint;
import com.kyloth.serleenacloud.persistence.IPathDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PathDao implements IPathDao {

    private JdbcTemplate tpl;

    PathDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public Iterable<Path> findAll(IRect region) {
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
                Iterable<IWeighedPoint> points = tpl.query("SELECT Latitude, Longitude, Radius " +
                                                 "FROM PathPoints " +
                                                 "WHERE PathName = ? "+
                                                 "ORDER BY Idx",
                                                 new Object[] {pathName},
                new RowMapper<IWeighedPoint>() {
                    @Override
                    public IWeighedPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new WeighedPoint(rs.getDouble("Latitude"), rs.getDouble("Longitude"),
                                                rs.getDouble("Radius"));
                    }
                });
                return new Path(points, pathName);
            }
        });
    }
}
