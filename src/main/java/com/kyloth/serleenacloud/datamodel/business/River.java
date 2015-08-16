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
 * Name: River.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.AWideLine;
import com.kyloth.serleenacloud.datamodel.geometry.Point;

/**
 * Classe che rappresenta un'entità di mappa di tipo fiume.
 *
 * @use Viene utilizzata da Render.Renderer durante la creazione dei quadranti raster
 * @field name : String Nome del fiume
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class River extends AWideLine {

    /**
     * Il nome del fiume.
     */

    private String name;

    /**
     * Crea un nuovo oggetto River inizializzandone i campi dati.
     *
     * @param points Insieme di Point che definiscono il fiume.
     * @param name Il nome del fiume.
     */

    public River(Iterable<Point> points, String name) {
        super(points);
        this.name = name;
    }

    /**
     * Crea un nuovo oggetto River inizializzandone i campi dati.
     *
     * @param points Array di IPoint che definiscono il fiume.
     * @param name Il nome del fiume.
     */

    public River(Point[] points, String name) {
        super(points);
        this.name = name;
    }

    /**
     * Metodo getter per ottenere il nome del fiume.
     *
     * @return Restituisce il nome del fiume.
     */

    public String getName() {
        return name;
    }

}
