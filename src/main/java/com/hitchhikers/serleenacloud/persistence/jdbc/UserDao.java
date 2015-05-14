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

import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.persistence.IUserDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements IUserDao {

    private JdbcTemplate tpl;

    UserDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }

    public void persist(User u) {
        if (find(u.getEmail()) == null)
            tpl.update("INSERT INTO users(email, password) VALUES (?, ?)",
                       new Object[] {u.getEmail(), u.getPassword()});
        else
            tpl.update("UPDATE users SET passwrd = ? WHERE email = ?",
                       new Object[] {u.getPassword(), u.getEmail()});
        if (u.getDeviceId() != null)
            tpl.update("UPDATE users SET deviceId = ? WHERE email = ?",
                       new Object[] {u.getDeviceId(), u.getEmail()});
        return;
    }

    public User find(final String email) {
        return tpl.query("SELECT password, deviceId " +
                         "FROM users " +
                         "WHERE email = ?",
                         new Object[] { email },
                         new ResultSetExtractor<User>() {
                             @Override
                             public User extractData(ResultSet rs) throws SQLException {
                                 return new User(email, rs.getString("password"), rs.getString("deviceId"));
                             }
                         });

    }
}
