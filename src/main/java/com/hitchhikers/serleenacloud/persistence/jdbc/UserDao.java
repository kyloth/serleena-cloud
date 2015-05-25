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
 * Name: UserDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.persistence.IUserDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza IUserDao per database MySQL utilizzando JDBC.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class UserDao implements IUserDao {

    private JdbcTemplate tpl;

    /**
     * Costruisce un nuovo UserDao.
     *
     * @param ds DataSource per la connessione al database.
     */
    
    UserDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
    }
    
    /**
     * Metodo che implementa IUserDao.persist(User).
     * 
     * @param token Utente da inserire.
     */

    public void persist(User u) {
        if (find(u.getEmail()) == null)
            tpl.update("INSERT INTO Users(Email, Password) VALUES (?, ?)",
                       new Object[] {u.getEmail(), u.getPassword()});
        else
            tpl.update("UPDATE Users SET Password = ? WHERE Email = ?",
                       new Object[] {u.getPassword(), u.getEmail()});
        if (u.getDeviceId() != null)
            tpl.update("UPDATE Users SET DeviceId = ? WHERE Email = ?",
                       new Object[] {u.getDeviceId(), u.getEmail()});
        return;
    }

    /**
     * Metodo che implementa IUserDao.find(String).
     *
     * @param email Email dell'utente che si vuole ottenere.
     */

    public User find(final String email) {
        return tpl.query("SELECT Password, DeviceId " +
                         "FROM Users " +
                         "WHERE Email = ?",
                         new Object[] { email },
        new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException {
                if (!rs.first())
                    return null;

                return new User(email, rs.getString("Password"), rs.getString("DeviceId"));
            }
        });

    }

    public User findDeviceId(final String deviceId) {
        return tpl.query("SELECT Email, Password " +
                         "FROM Users " +
                         "WHERE DeviceId = ?",
                         new Object[] { deviceId },
        new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException {
                if (!rs.first())
                    return null;

                return new User(rs.getString("Email"), rs.getString("Password"), deviceId);
            }
        });

    }
}
