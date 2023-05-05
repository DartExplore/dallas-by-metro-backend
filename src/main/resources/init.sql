DROP SCHEMA if EXISTS dart_explore;
CREATE SCHEMA dart_explore;
use dart_explore;

DROP TABLE if EXISTS station;
CREATE TABLE station (
    station_id INTEGER PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255)
);

DROP TABLE if EXISTS points_of_interest;
CREATE TABLE points_of_interest (
    poi_id INTEGER PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    walking_distance INTEGER,
    pic_url VARCHAR(255),
    type VARCHAR(255),
    station_id INTEGER,
    FOREIGN KEY (station_id) REFERENCES station (station_id)
);

DROP TABLE if EXISTS amenity;
CREATE TABLE amenity (
    amenity_id INTEGER,
    amenity VARCHAR(255),
    PRIMARY KEY (amenity_id, amenity),
    FOREIGN KEY (amenity_id) REFERENCES points_of_interest (poi_id),
    CONSTRAINT amenity_amenity_check CHECK (amenity IN ('amenity1', 'amenity2', 'amenity3'))
);

DROP TABLE if EXISTS color;
CREATE TABLE color (
    color VARCHAR(16) PRIMARY KEY,
    CONSTRAINT color_check CHECK (color IN ('BLUE', 'RED', 'ORANGE', 'GREEN'))
);

DROP TABLE if EXISTS station_connection;
CREATE TABLE station_connection (
    station1_id INTEGER,
    station2_id INTEGER,
    color VARCHAR(16),
    PRIMARY KEY (station1_id, station2_id, color),
    FOREIGN KEY (station1_id) REFERENCES station (station_id),
    FOREIGN KEY (station2_id) REFERENCES station (station_id),
    FOREIGN KEY (color) REFERENCES color (color)
);

DROP TABLE if EXISTS poi_amenity;
CREATE TABLE poi_amenity (
    poi INTEGER,
    amenity INTEGER,
    PRIMARY KEY (poi, amenity),
    FOREIGN KEY (poi) REFERENCES points_of_interest (poi_id),
    FOREIGN KEY (amenity) REFERENCES amenity (amenity_id)
);
