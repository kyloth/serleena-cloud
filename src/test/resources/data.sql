INSERT INTO POIs (Name, Longitude, Latitude, Type) VALUES ('POI1', 6, 6, 'INFO'), ('POI2', 7, 7, 'FOOD');

INSERT INTO EmergencyContacts (Name, Number, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES ('Emergency_1', 1, 1, 10, 10, 1);

INSERT INTO Paths (Name) VALUES ('foo');

INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.276413, 11.650587, 0);
INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.275977, 11.652806, 1);
INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.276257, 11.654908, 2);
INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.277573, 11.654297, 3);
INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.278192, 11.655213, 4);
INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.279032, 11.655626, 5);
INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.280266, 11.655875, 6);
INSERT INTO PathPoints (PathName, Latitude, Longitude, Idx) VALUES ('foo', 45.281726, 11.655550, 7);

INSERT INTO WeatherForecasts (MTemperature, ATemperature, NTemperature, Date, MForecast, AForecast, NForecast, NWLongitude, NWLatitude, SELongitude, SELatitude) VALUES (12, 12, 12, '1980-01-01 00:00:01', 'CLOUDY', 'CLOUDY', 'CLOUDY', 6, 15, 15, 6), (13, 13, 13, '1975-01-01 00:00:01', 'SUNNY', 'SUNNY', 'SUNNY', 6, 15, 15, 6), (14, 14, 14, '1980-01-01 00:00:01','SNOWY', 'SNOWY', 'SNOWY', 30, 20, 20, 30);
