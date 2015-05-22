 CREATE TABLE Users(Email VARCHAR(32) PRIMARY KEY, Password VARCHAR(32) NOT NULL, DeviceId VARCHAR(32) UNIQUE)

CREATE TABLE Experiences(Name VARCHAR(32) PRIMARY KEY, User VARCHAR(32) NOT NULL, NWLongitude REAL NOT NULL, NWLatitude REAL NOT NULL, SELongitude REAL NOT NULL, SELatitude REAL NOT NULL, FOREIGN KEY(User) REFERENCES Users(Email))

CREATE TABLE Tracks(Name VARCHAR(32) PRIMARY KEY)

CREATE TABLE Checkpoints(Id INTEGER PRIMARY KEY AUTO_INCREMENT, TrackName VARCHAR(32) NOT NULL, Longitude REAL NOT NULL, Latitude REAL NOT NULL, Idx INTEGER NOT NULL, FOREIGN KEY(TrackName) REFERENCES Tracks(Name))

CREATE TABLE Telemetries(Id INTEGER PRIMARY KEY AUTO_INCREMENT, TrackName VARCHAR(32) NOT NULL, FOREIGN KEY(TrackName) REFERENCES Tracks(Name))

CREATE TABLE TelemetryEvents(Id INTEGER PRIMARY KEY AUTO_INCREMENT, TelemetryId INTEGER NOT NULL, Value REAL NOT NULL, Type VARCHAR(32) NOT NULL, Date TIMESTAMP NOT NULL, FOREIGN KEY(TelemetryId) REFERENCES Telemetries(Id))

CREATE TABLE ExperienceTracks(Id INTEGER PRIMARY KEY AUTO_INCREMENT, ExperienceName VARCHAR(32) NOT NULL, TrackName VARCHAR(32) NOT NULL, FOREIGN KEY(ExperienceName) REFERENCES Experiences(Name), FOREIGN KEY(TrackName) REFERENCES Tracks(Name))

CREATE TABLE ExperienceUserPoints(Id INTEGER PRIMARY KEY AUTO_INCREMENT, ExperienceName VARCHAR(32) NOT NULL, Name VARCHAR(32) NOT NULL, Longitude REAL NOT NULL, Latitude REAL NOT NULL, FOREIGN KEY(ExperienceName) REFERENCES Experiences(Name))

CREATE TABLE POIs(Name VARCHAR(32) PRIMARY KEY, Longitude REAL NOT NULL, Latitude REAL NOT NULL, Type VARCHAR(32) NOT NULL)

CREATE TABLE ExperiencePOIs(Id INTEGER PRIMARY KEY AUTO_INCREMENT, ExperienceName VARCHAR(32) NOT NULL, POIName VARCHAR(32) NOT NULL, FOREIGN KEY(ExperienceName) REFERENCES Experiences(Name), FOREIGN KEY(POIName) REFERENCES POIs(Name))

CREATE TABLE Lakes(Name VARCHAR(32) PRIMARY KEY)

CREATE TABLE LakePoints(Id INTEGER PRIMARY KEY AUTO_INCREMENT, LakeName VARCHAR(32) NOT NULL, Longitude REAL NOT NULL, Latitude REAL NOT NULL, Idx INTEGER NOT NULL, FOREIGN KEY(LakeName) REFERENCES Lakes(Name))

CREATE TABLE Rivers(Name VARCHAR(32) PRIMARY KEY)

CREATE TABLE RiverPoints(Id INTEGER PRIMARY KEY AUTO_INCREMENT, RiverName VARCHAR(32) NOT NULL, Longitude REAL NOT NULL, Latitude REAL NOT NULL, Radius REAL NOT NULL, Idx INTEGER NOT NULL, FOREIGN KEY(RiverName) REFERENCES Rivers(Name))

CREATE TABLE Paths(Name VARCHAR(32) PRIMARY KEY)

CREATE TABLE PathPoints(Id INTEGER PRIMARY KEY AUTO_INCREMENT, PathName VARCHAR(32) NOT NULL, Longitude REAL NOT NULL, Latitude REAL NOT NULL, Radius REAL NOT NULL, Idx INTEGER NOT NULL, FOREIGN KEY(PathName) REFERENCES Paths(Name))

CREATE TABLE EmergencyContacts(Id INTEGER PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(32) NOT NULL, Number VARCHAR(32) NOT NULL, NWLongitude REAL NOT NULL, NWLatitude REAL NOT NULL, SELongitude REAL NOT NULL, SELatitude REAL NOT NULL)

CREATE TABLE WeatherForecasts(Id INTEGER PRIMARY KEY AUTO_INCREMENT, Temperature REAL NOT NULL, Date TIMESTAMP NOT NULL, Forecast VARCHAR(32) NOT NULL, NWLongitude REAL NOT NULL, NWLatitude REAL NOT NULL, SELongitude REAL NOT NULL, SELatitude REAL NOT NULL)

CREATE TABLE ElevantionRect(Id INTEGER PRIMARY KEY AUTO_INCREMENT, Height REAL NOT NULL, NWLongitude REAL NOT NULL, NWLatitude REAL NOT NULL, SELongitude REAL NOT NULL, SELatitude REAL NOT NULL)