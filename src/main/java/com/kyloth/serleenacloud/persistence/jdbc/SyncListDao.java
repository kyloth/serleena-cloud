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

import com.kyloth.serleenacloud.datamodel.business.IExperience;
import com.kyloth.serleenacloud.datamodel.auth.User;

import com.kyloth.serleenacloud.persistence.IExperienceDao;
import com.kyloth.serleenacloud.persistence.ISyncListDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SyncListDao implements ISyncListDao {

    private JdbcTemplate tpl;
    private User user;
    private IExperienceDao ed;

    SyncListDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
        this.user = ds.getUser();
        this.ed = ds.experienceDao();
    }

    public void persist(Iterable<IExperience> es) {
        tpl.update("DELETE FROM SyncList WHERE User = ?", new Object[] {user.getEmail()});

        for (IExperience e : es)
            tpl.update("INSERT INTO SyncList(ExperienceName, User) VALUES(?, ?)",
                       new Object[] {e.getName(), user.getEmail()});
    }

    public Iterable<IExperience> findAll() {
        return tpl.query("SELECT ExperienceName FROM SyncList WHERE User = ?",
                         new Object[] {user.getEmail()},
                         new RowMapper<IExperience>() {
                             @Override
                             public IExperience mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 return ed.find(rs.getString("ExperienceName"));
                             }
                         });
    }

}
