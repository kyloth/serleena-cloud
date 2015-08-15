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
 * Name: SyncListDao.java
 * Package: com.kyloth.serleenacloud.persistence.jdbc
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence.jdbc;

import com.kyloth.serleenacloud.datamodel.business.Experience;
import com.kyloth.serleenacloud.datamodel.auth.User;

import com.kyloth.serleenacloud.persistence.IExperienceDao;
import com.kyloth.serleenacloud.persistence.ISyncListDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che concretizza ISyncListDao per database MySQL utilizzando JDBC.
 *
 * @field tpl : JdbcTemplate Template JDBC per la connessione alla base di dati
 * @field user : User Oggetto rappresentante l'utente i quali dati si vogliono manipolare
 * @field ed : IExperienceDao DAO per oggetti di tipo Experience
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class SyncListDao implements ISyncListDao {

    /**
     * Template JDBC per la connessione alla base di dati.
     */

    private JdbcTemplate tpl;
    
    /**
     * Utente i quali dati si vogliono manipolare.
     */

    private User user;

    /**
     * DAO per oggetti di tipo Experience.
     */

    private IExperienceDao ed;

    /**
     * Costruisce un nuovo SyncListDao.
     *
     * @param ds DataSource per la connessione al database.
     */

    SyncListDao(JDBCDataSource ds) {
        this.tpl = ds.getTpl();
        this.user = ds.getUser();
        this.ed = ds.experienceDao();
    }

    /**
     * Metodo che implementa ISyncListDao.persist(Iterable<Experience>).
     *
     * @param es L'Esperienza da inserire nella lista.
     */

    public void persist(Iterable<Experience> es) {
        tpl.update("DELETE FROM SyncList WHERE User = ?", new Object[] {user.getEmail()});

        for (Experience e : es)
            tpl.update("INSERT INTO SyncList(ExperienceId, User) VALUES(?, ?)",
                       new Object[] {e.getId(), user.getEmail()});
    }

    /**
     * Metodo che implementa ISyncListDao.findAll().
     *
     * @return Restituisce la lista delle Esperienze in lista di sincronizzazione.
     */

    public Iterable<Experience> findAll() {
        return tpl.query("SELECT ExperienceId FROM SyncList WHERE User = ?",
                         new Object[] {user.getEmail()},
                         new RowMapper<Experience>() {
                             @Override
                             public Experience mapRow(ResultSet rs, int rowNum) throws SQLException {
                                 return ed.find(rs.getString("ExperienceId"));
                             }
                         });
    }

}
