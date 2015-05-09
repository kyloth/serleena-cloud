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


package com.kyloth.serleenacloud.datamodel.business;

import com.kyloth.serleenacloud.datamodel.geometry.IRect;

import java.util.Date;

public class EmergencyContact implements IEmergencyContact {

    private IRect boundingRect;
    private String name;
    private String number;

    public EmergencyContact(String name, IRect boundingRect, String number) {
        this.name = name;
        this.boundingRect = boundingRect;
        this.number = number;
    }

    public IRect getBoundingRect() {
        return boundingRect;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
