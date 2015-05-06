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
 * Name: IPoly.java
 * Package: com.kyloth.serleenacloud.datamodel.geometry
 * Author: Nicola Mometto
 * Date: 2015-05-05
 *
 * History:
 * Version  Programmer          Date        Changes
 * 1.0.0    Nicola Mometto      2015-05-05  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.geometry;

/**
 * Interfaccia rappresentante un'area poligonale delimitata da coordinate geografiche
 *
 * @author  Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 * @since   2015-05-06
 */

public interface IPoly {

    /**
     * Ritorna true se il punto e` contenuto nell'area del poligono
     *
     * @param  Il punto da testare
     * @return True se il punto e` contenuto nel poligono, altrimenti false
     */

    public boolean contains(IPoint p);

    /**
     * Ritorna la collezione dei punti che delimitano il perimetro del poligono
     *
     * @return La collezione dei punti che delimitano il perimetro del poligono
     */

    public Iterable<IPoint> getPoints();

    /**
     * Ritorna true se il poligono corrente crea un'intersezione non vuota con
     * il poligono da testare
     *
     * @param  Il poligono da testare
     * @return True se l'intersezione tra i due poligoni e` non vuota, false altrimenti
     */

    public boolean intersects(IPoly p);
}
