# dart-explore-backend

![Train going through Downtown.](https://texashighways.com/wp-content/uploads/2015/06/images_dart2.jpg)

[![Integration Testing](https://github.com/DartExplore/dart-explore-backend/actions/workflows/integrationTests.yml/badge.svg)](https://github.com/DartExplore/dart-explore-backend/actions/workflows/integrationTests.yml)
[![Build](https://github.com/DartExplore/dart-explore-backend/actions/workflows/build.yml/badge.svg)](https://github.com/DartExplore/dart-explore-backend/actions/workflows/build.yml)
[![Deploy](https://github.com/DartExplore/dart-explore-backend/actions/workflows/deploy.yml/badge.svg)](https://github.com/DartExplore/dart-explore-backend/actions/workflows/deploy.yml)

Backend for Dallas by Metro using Spring Boot. Database implemented in Postgres. We expose the backend through a RESTful
API.

## How to Run

Simply clone the repository and open the project in your IDE of choice. If application cannot run, ensure you have a
recent version of Java and run a Maven update. A test database can be spun up in Docker by navigating to the resources
folder and executing *sudo docker compose up* and deleted by *sudo docker compose down -v*.

## Database

![Database diagram.](https://user-images.githubusercontent.com/85081861/238772266-278600a4-21c6-44f1-af4c-049acbb6e44a.png)

## API

There is a public API for read operations and a private API for database operations such as creating, updating, and
deleting point of interest and amenity data.

The current public API layout is as follows:

| Route                                           | Request Type | Description                                      | Return                  |
|-------------------------------------------------|--------------|--------------------------------------------------|-------------------------|
| /api/public/poi/amenity/{amenities}             | GET          | Gets POIs with given amenities.                  | List\<PointOfInterest\> |
| /api/public/station/{line}                      | GET          | Gets all stations on a given line.               | List\<Station\>         |
| /api/public/poi/{stationId}                     | GET          | Gets all POIs at a station.                      | List\<PointOfInterest\> |
| /api/public/poi/{stationId}/amenity/{amenities} | GET          | Gets all POIs at a station with given amenities. | List\<PointOfInterest\> |

The current private API layout is as follows:

| Route                 | Request Type | Description                                                   | Return          |
|-----------------------|--------------|---------------------------------------------------------------|-----------------|
| /api/private/poi/     | POST         | Creates new POI and returns POI with DB generated ID.         | PointOfInterest | 
| /api/private/poi/     | PUT          | Updates POI.                                                  | PointOfInterest |
| /api/private/poi/     | DELETE       | Deletes POI with status message.                              | String          |
| /api/private/amenity/ | POST         | Creates new Amenity and returns Amenity with DB generated ID. | Amenity         |
| /api/private/amenity/ | PUT          | Updates Amenity.                                              | Amenity         |
| /api/private/amenity/ | DELETE       | Deletes Amenity with status message.                          | String          |

## Getting Involved

This project was started to help residents of Dallas learn to use the DART train system but this code can be easily
extended to other transit systems.
We encourage forking and making your own version or contacting us directly.
