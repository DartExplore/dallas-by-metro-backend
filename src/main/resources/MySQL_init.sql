DROP TABLE IF EXISTS station;
CREATE TABLE station
(
    station_id INT PRIMARY KEY,
    name       VARCHAR(255),
    latitude   DOUBLE,
    longitude  DOUBLE
);

INSERT INTO station (station_id, name)
VALUES (1, 'A'),
       (2, 'B'),
       (3, 'C'),
       (4, 'D'),
       (5, 'E'),
       (6, 'F');

DROP TABLE IF EXISTS points_of_interest;
CREATE TABLE points_of_interest
(
    poi_id           INT PRIMARY KEY,
    name             VARCHAR(255),
    location         VARCHAR(255),
    walking_distance INT,
    pic_url          VARCHAR(255),
    type             VARCHAR(255),
    station_id       INT,
    FOREIGN KEY (station_id) REFERENCES station (station_id)
);

INSERT INTO points_of_interest (poi_id, name, station_id)
VALUES (1, 'PA1', 1),
       (2, 'PB1', 2),
       (3, 'PC1', 3),
       (4, 'PD1', 4),
       (5, 'PE1', 5),
       (6, 'PE2', 5),
       (7, 'PF1', 6);

DROP TABLE IF EXISTS amenity;
CREATE TABLE amenity
(
    amenity_id INT PRIMARY KEY,
    amenity    VARCHAR(255)
);

INSERT INTO amenity (amenity_id, amenity)
VALUES (1, 'A1'),
       (2, 'A2'),
       (3, 'A3');

DROP TABLE IF EXISTS station_connection;
CREATE TABLE station_connection
(
    station1_id INT,
    station2_id INT,
    PRIMARY KEY (station1_id, station2_id),
    FOREIGN KEY (station1_id) REFERENCES station (station_id),
    FOREIGN KEY (station2_id) REFERENCES station (station_id)
);

INSERT INTO station_connection (station1_id, station2_id)
VALUES (1, 2),
       (2, 3),
       (3, 4),
       (4, 5),
       (4, 6);

DROP TABLE IF EXISTS station_color;
CREATE TABLE station_color
(
    station_id INT,
    color      VARCHAR(16),
    PRIMARY KEY (station_id, color),
    FOREIGN KEY (station_id) REFERENCES station (station_id)
);

INSERT INTO station_color (station_id, color)
VALUES (1, 'RED'),
       (2, 'RED'),
       (2, 'BLUE'),
       (3, 'RED'),
       (3, 'BLUE'),
       (4, 'RED'),
       (5, 'BLUE');

DROP TABLE IF EXISTS poi_amenity;
CREATE TABLE poi_amenity
(
    poi_id     INT,
    amenity_id INT,
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
FROM station_connection;
SELECT *
FROM station_color;
SELECT *
FROM points_of_interest;
SELECT *
FROM amenity;
SELECT *
FROM poi_amenity;
