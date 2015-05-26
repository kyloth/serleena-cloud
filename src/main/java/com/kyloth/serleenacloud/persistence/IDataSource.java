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

import com.kyloth.serleenacloud.datamodel.auth.User;

public interface IDataSource {
    public IUserDao userDao();
    public IPointOfInterestDao pointOfInterestDao();
    public IPathDao pathDao();
    public ILakeDao lakeDao();
    public IRiverDao riverDao();
    public IEmergencyContactDao emergencyContactDao();
    public ITelemetryDao telemetryDao();
    public IWeatherForecastDao weatherForecastDao();
    public ITrackDao trackDao();
    public ISyncListDao syncListDao();
    public IExperienceDao experienceDao();
    public IDataSource forUser(User u);
}