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


package com.kyloth.serleenacloud;

/**
 * Example Spring bean
 */

public class Hello {
    private String name;

    public void setName(String name) {
	this.name = name;
    }
 
    public void printHello() {
	System.out.println("Vai " + name);
    }
}
