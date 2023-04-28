CREATE TABLE station (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255)

);

CREATE TABLE points_of_interest (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    walking_distance INTEGER,
    pic_url VARCHAR(255),
    type VARCHAR(255),
    station_id INTEGER,
    FOREIGN KEY (station_id) REFERENCES station (id)
);

CREATE TABLE amenity (
    poi_id INTEGER,
    amenity VARCHAR(255),
    PRIMARY KEY (poi_id, amenity),
    FOREIGN KEY (poi_id) REFERENCES points_of_interest (id),
    CONSTRAINT amenity_amenity_check CHECK (amenity IN ('amenity1', 'amenity2', 'amenity3'))
);

CREATE TABLE station_color (
    station_id INTEGER,
    color VARCHAR(255),
    PRIMARY KEY (station_id, color),
    FOREIGN KEY (station_id) REFERENCES station (id)
);

CREATE TABLE station_connection (
    station1_id INTEGER,
    station2_id INTEGER,
    PRIMARY KEY (station1_id, station2_id),
    FOREIGN KEY (station1_id) REFERENCES station (id),
    FOREIGN KEY (station2_id) REFERENCES station (id)
);
