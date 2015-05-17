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
 * Name: ITelemetry.java
 * Package: com.kyloth.serleenacloud.datamodel.business
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.datamodel.business;

/**
 * Interfaccia che rappresenta informazioni di Tracciamento.
 *
 * @use Viene utilizzata da DataRestController e ExperienceRestController per creare o elaborare il JSON fornito o richiesto da frontend e applicazione android
 *
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public interface ITelemetry extends Comparable<ITelemetry> {

    /**
     * Metodo "getter" che permette di ottenere l'insieme degli eventi associati al Tracciamento.
     *
     * @return Restituisce l'insieme degli eventi associati al Tracciamento.
     */

    Iterable<TelemetryEvent> getEvents();
}
