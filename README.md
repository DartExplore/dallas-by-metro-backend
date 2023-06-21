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
folder and executing *sudo docker compose up* and deleted by *sudo docker compose down -v*. Some test data can be generated
by running the NodeJS script under the scripts folder within resources.

## Database

![Database diagram.](https://user-images.githubusercontent.com/85081861/238772266-278600a4-21c6-44f1-af4c-049acbb6e44a.png)

## API

There is a public API for read operations and a private API for database operations such as creating, updating, and
deleting point of interest and amenity data.

The current public API layout is as follows:

![image](https://github.com/DartExplore/dallas-by-metro-backend/assets/18405628/9fc479d5-8ac1-40e2-ae44-3bc1e9159f2f)

This changes frequently at the moment so we suggest going to the following [link](https://dallasbymetro.com/swagger-ui/index.html).

## Getting Involved

This project was started to help residents of Dallas learn to use the DART train system but this code can be easily
extended to other transit systems.
We encourage forking to make your own version or contacting us directly.
