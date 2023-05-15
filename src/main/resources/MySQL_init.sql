DROP SCHEMA if EXISTS dart_explore;
CREATE SCHEMA dart_explore;
use dart_explore;

DROP TABLE if EXISTS station;
CREATE TABLE station
(
    station_id INTEGER PRIMARY KEY,
    name       VARCHAR(255),
    location   VARCHAR(255)
);

INSERT INTO station (station_id, name)
VALUES (1, 'A');
INSERT INTO station (station_id, name)
VALUES (2, 'B');
INSERT INTO station (station_id, name)
VALUES (3, 'C');
INSERT INTO station (station_id, name)
VALUES (4, 'D');
INSERT INTO station (station_id, name)
VALUES (5, 'E');
INSERT INTO station (station_id, name)
VALUES (6, 'F');

DROP TABLE if EXISTS points_of_interest;
CREATE TABLE points_of_interest
(
    poi_id           INTEGER PRIMARY KEY,
    name             VARCHAR(255),
    location         VARCHAR(255),
    walking_distance INTEGER,
    pic_url          VARCHAR(255),
    type             VARCHAR(255),
    station_id       INTEGER,
    FOREIGN KEY (station_id) REFERENCES station (station_id)
);

INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (1, 'PA1', 1);
INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (2, 'PB1', 2);
INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (3, 'PC1', 3);
INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (4, 'PD1', 4);
INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (5, 'PE1', 5);
INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (6, 'PE1', 5);
INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (7, 'PF1', 6);

DROP TABLE if EXISTS amenity;
CREATE TABLE amenity
(
    amenity_id INTEGER,
    amenity    VARCHAR(255),
    PRIMARY KEY (amenity_id, amenity),
    FOREIGN KEY (amenity_id) REFERENCES points_of_interest (poi_id)
);

INSERT INTO amenity (amenity_id, amenity)
VALUES (1, 'A1');
INSERT INTO amenity (amenity_id, amenity)
VALUES (2, 'A2');
INSERT INTO amenity (amenity_id, amenity)
VALUES (3, 'A3');

DROP TABLE if EXISTS color;
CREATE TABLE color
(
    color VARCHAR(16) PRIMARY KEY,
    CONSTRAINT color_check CHECK (color IN ('BLUE', 'RED', 'ORANGE', 'GREEN'))
);

INSERT INTO color
VALUES ('BLUE');
INSERT INTO color
VALUES ('RED');
INSERT INTO color
VALUES ('ORANGE');
INSERT INTO color
VALUES ('GREEN');

DROP TABLE if EXISTS station_connection;
CREATE TABLE station_connection
(
    station1_id INTEGER,
    station2_id INTEGER,
    color       VARCHAR(16),
    PRIMARY KEY (station1_id, station2_id, color),
    FOREIGN KEY (station1_id) REFERENCES station (station_id),
    FOREIGN KEY (station2_id) REFERENCES station (station_id),
    FOREIGN KEY (color) REFERENCES color (color)
);

INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (1, 2, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (2, 1, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (2, 3, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (2, 3, 'BLUE');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (3, 2, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (3, 2, 'BLUE');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (3, 4, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (3, 4, 'BLUE');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (4, 3, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (4, 3, 'BLUE');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (4, 5, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (5, 4, 'RED');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (4, 6, 'BLUE');
INSERT INTO station_connection (station1_id, station2_id, color)
VALUES (6, 4, 'BLUE');

DROP TABLE if EXISTS poi_amenity;
CREATE TABLE poi_amenity
(
    poi     INTEGER,
    amenity INTEGER,
    PRIMARY KEY (poi, amenity),
    FOREIGN KEY (poi) REFERENCES points_of_interest (poi_id),
    FOREIGN KEY (amenity) REFERENCES amenity (amenity_id)
);

INSERT INTO poi_amenity (poi, amenity)
VALUES (1, 1);
INSERT INTO poi_amenity (poi, amenity)
VALUES (2, 2);
INSERT INTO poi_amenity (poi, amenity)
VALUES (3, 1);
INSERT INTO poi_amenity (poi, amenity)
VALUES (4, 1);
INSERT INTO poi_amenity (poi, amenity)
VALUES (4, 3);
INSERT INTO poi_amenity (poi, amenity)
VALUES (5, 1);
INSERT INTO poi_amenity (poi, amenity)
VALUES (5, 2);
INSERT INTO poi_amenity (poi, amenity)
VALUES (6, 2);
INSERT INTO poi_amenity (poi, amenity)
VALUES (7, 3);

SELECT *
FROM station;
SELECT *
FROM color;
SELECT *
FROM station_connection;
SELECT *
FROM points_of_interest;
SELECT *
FROM amenity;
SELECT *
FROM poi_amenity;
