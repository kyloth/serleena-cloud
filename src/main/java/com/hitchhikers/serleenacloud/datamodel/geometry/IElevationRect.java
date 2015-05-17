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
 * Name: IElevationRect.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

/**
 * Interfaccia per oggetti rappresentanti un'area rettangolare di mappa a cui
 * e` associata un'altezza
 *
 * @use Viene utilizzata per rappresentare le coordinate altimetriche della mappa
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface IElevationRect extends IRect {

    /**
     * Ritorna l'altezza associata con l'area rettangolare di mappa
     *
     * @return L'altezza associata
     */

    public double getHeight();
}
