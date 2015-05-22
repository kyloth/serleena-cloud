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
 * Name: Path.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.AWideLine;
import com.kyloth.serleenacloud.datamodel.geometry.IWeighedPoint;

/**
 * Classe che rappresenta un'entità di mappa di tipo sentiero.
 *
 * @use Viene utilizzata da Render.Renderer durante la creazione dei quadranti raster
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Path extends AWideLine {

    /**
     * Nome del sentiero.
     */

    private String name;

    /**
     * Crea un nuovo oggetto Path inizializzandone i campi dati.
     *
     * @param points Insieme di IWeighedPoint che definiscono il sentiero.
     * @param name Il nome del sentiero.
     */

    public Path(Iterable<IWeighedPoint> points, String name) {
        super(points);
        this.name = name;
    }

    /**
     * Crea un nuovo oggetto Path inizializzandone i campi dati.
     *
     * @param points Array di IWeighedPoint che definiscono il sentiero.
     * @param name Il nome del sentiero.
     */

    public Path(IWeighedPoint[] points, String name) {
        super(points);
        this.name = name;
    }

    /**
     * Metodo "getter" che permette di ottenere il nome del sentiero.
     *
     * @return Restituisce il nome del sentiero.
     */

    public String getName() {
        return name;
    }

}
