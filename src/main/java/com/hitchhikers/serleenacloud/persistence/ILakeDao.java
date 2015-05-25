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
 * Name: ILakeDao.java
 * Package: com.kyloth.serleenacloud.persistence
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.business.Lake;
import com.kyloth.serleenacloud.datamodel.geometry.Rect;

/**
 * Interfaccia implementata da una classe che realizza la persistenza su database degli oggetti di tipo Lake.
 *
 * @use Contiene metodi per cercare nella base di dati i laghi in una certa regione di mappa.
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface ILakeDao {
    
    /**
     * Permette di ottenere tutti i laghi relativi a una certa area di mappa.
     *
     * @param region La regione di interesse.
     * @return Restituisce la lista dei laghi relativi alla regione specificata.
     */

    public Iterable<Lake> findAll(Rect region);
}
