CREATE SCHEMA IF NOT EXISTS dart_explore;
SET search_path TO dart_explore;

CREATE TABLE IF NOT EXISTS station
(
    station_id INTEGER PRIMARY KEY,
    name       VARCHAR(255),
    location   VARCHAR(255)
);

INSERT INTO station (station_id, name)
VALUES (1, 'A'),
       (2, 'B'),
       (3, 'C'),
       (4, 'D'),
       (5, 'E'),
       (6, 'F');

CREATE TABLE IF NOT EXISTS points_of_interest
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
VALUES (1, 'PA1', 1),
       (2, 'PB1', 2),
       (3, 'PC1', 3),
       (4, 'PD1', 4),
       (5, 'PE1', 5),
       (6, 'PE1', 5),
       (7, 'PF1', 6);

CREATE TABLE IF NOT EXISTS amenity
(
    amenity_id INTEGER PRIMARY KEY,
    amenity    VARCHAR(255)
);

INSERT INTO amenity (amenity_id, amenity)
VALUES (1, 'A1'),
       (2, 'A2'),
       (3, 'A3');

CREATE TABLE IF NOT EXISTS color
(
    color VARCHAR(16) PRIMARY KEY,
    CONSTRAINT color_check CHECK (color IN ('BLUE', 'RED', 'ORANGE', 'GREEN'))
);

INSERT INTO color
VALUES ('BLUE'),
       ('RED'),
       ('ORANGE'),
       ('GREEN');

CREATE TABLE IF NOT EXISTS connection
(
    station1_id INTEGER,
    station2_id INTEGER,
    PRIMARY KEY (station1_id, station2_id),
    FOREIGN KEY (station1_id) REFERENCES station (station_id),
    FOREIGN KEY (station2_id) REFERENCES station (station_id)
);

INSERT INTO connection (station1_id, station2_id)
VALUES (1, 2),
       (2, 3),
       (3, 4),
       (4, 5),
       (4, 6);

CREATE TABLE IF NOT EXISTS connection_color
(
    station_id INTEGER,
    color      VARCHAR(16),
    PRIMARY KEY (station_id, color),
    FOREIGN KEY (station_id) REFERENCES station (station_id),
    FOREIGN KEY (color) REFERENCES color (color)
);

INSERT INTO connection_color (station_id, color)
VALUES (1, 'RED'),
       (2, 'RED'),
       (2, 'BLUE'),
       (3, 'RED'),
       (3, 'BLUE'),
       (4, 'RED'),
       (5, 'BLUE');

CREATE TABLE IF NOT EXISTS poi_amenity
(
    poi_id     INTEGER,
    amenity_id INTEGER,
    PRIMARY KEY (poi_id, amenity_id),
    FOREIGN KEY (poi_id) REFERENCES points_of_interest (poi_id),
    FOREIGN KEY (amenity_id) REFERENCES amenity (amenity_id)
);

INSERT INTO poi_amenity (poi_id, amenity_id)
VALUES (1, 1),
       (2, 2),
       (3, 1),
       (4, 1),
       (4, 3),
       (5, 1),
       (5, 2),
       (6, 2),
       (7, 3);

SELECT *
FROM station;
SELECT *
FROM color;
SELECT *
FROM connection;
SELECT *
FROM connection_color;
SELECT *
FROM points_of_interest;
SELECT *
FROM amenity;
SELECT *
FROM poi_amenity;
