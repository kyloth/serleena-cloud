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
 * Name: IRect.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

/**
 * Interfaccia rappresentante un'area rettangolare delimitata da coordinate geografiche
 *
 * @use Contiene metodi per ottenere i due vertici da cui è delimitato il rettangolo. Viene utilizzata da numerose classi nel data model quando è necessario rappresentare una regione di mappa
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0 
 */

public interface IRect extends IPoly {

    /**
     * Ritorna la coordinata geografica dell'angolo Nord-Ovest del rettangolo
     *
     * @return La coordinata geografica dell'angolo Nord-Ovest
     */

    public IPoint getNWPoint();

    /**
     * Ritorna la coordinata geografica dell'angolo Sud-Est del rettangolo
     *
     * @return La coordinata geografica dell'angolo Sud-Est
     */

    public IPoint getSEPoint();
}
