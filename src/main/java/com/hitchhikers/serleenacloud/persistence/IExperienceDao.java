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


package com.kyloth.serleenacloud.persistence;

import com.kyloth.serleenacloud.datamodel.business.Experience;

public interface IExperienceDao {
    public void persist(Experience e);
    public Iterable<Experience> findAll();
    public Experience find(String name);
    public void delete(String name);
}
