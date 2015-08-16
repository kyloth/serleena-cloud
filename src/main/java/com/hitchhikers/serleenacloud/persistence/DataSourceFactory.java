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
 * Name: DataSourceFactory.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe factory per la creazione di un oggetto IDataSource.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class DataSourceFactory {
    
    /**
     * Restituisce una classe factory per la creazione di oggetti DAO.
     *
     * @return Restituisce un IDataSource per ottenere oggetti DAO.
     */

    public static IDataSource getDataSource() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
        return (IDataSource) context.getBean("dataSource");
    }
}
