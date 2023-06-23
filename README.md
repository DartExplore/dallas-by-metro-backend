# dart-explore-backend

![Train going through Downtown.](https://texashighways.com/wp-content/uploads/2015/06/images_dart2.jpg)

[![Integration Testing](https://github.com/DartExplore/dart-explore-backend/actions/workflows/integrationTests.yml/badge.svg)](https://github.com/DartExplore/dart-explore-backend/actions/workflows/integrationTests.yml)
[![Build](https://github.com/DartExplore/dart-explore-backend/actions/workflows/build.yml/badge.svg)](https://github.com/DartExplore/dart-explore-backend/actions/workflows/build.yml)
[![Deploy](https://github.com/DartExplore/dart-explore-backend/actions/workflows/deploy.yml/badge.svg)](https://github.com/DartExplore/dart-explore-backend/actions/workflows/deploy.yml)

Backend for Dallas by Metro using Spring Boot. Database implemented in Postgres. We expose the backend through a RESTful
API. Hosted on AWS

## How to Run the Project

Follow the steps below to clone and run this project on your local machine:

1. **Clone the Repository:** Clone the repository to your local machine.

2. **Install Dependencies:** Make sure you have a recent version of the required tools installed:
    - Java
    - Maven: Navigate to the project directory and run a Maven update to resolve dependencies.
    - Docker
    - NodeJS

3. **Set environment variables**
    - If you're using intellij IDEA you can copy this into your environment variables. Otherwise, look into how to set
      env variables for your IDE.
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/dart_explore;SPRING_DATASOURCE_USERNAME=root;SPRING_DATASOURCE_PASSWORD=password;API_KEY=password;PROFILE=dev
   ```

4. **Set up the Local Development Database:**
    - We use Docker to create a database for local development and testing purposes.To use, navigate to the `resources`
      folder in the project directory.
    - To spin up the local development database, run `sudo docker compose up`
        - This command sets up and starts a Dockerized database that you can use for testing and local development.
    - When you're done and want to delete the database, you can run `sudo docker compose down -v`
        - This command will spin down the database. The `-v` flag is important as it removes the volumes associated with
          the database, ensuring a clean state for future test runs.
5. **Generate example Data:**
    - To generate example data for the local development database, use the NodeJS script located in the `scripts` folder
      within the `resources` directory.
    - Run this script to generate and insert example data into your local development database.

## Data Model

![Database diagram.](https://user-images.githubusercontent.com/85081861/238772266-278600a4-21c6-44f1-af4c-049acbb6e44a.png)

## API

This project provides two types of APIs:

- **Public API:** This API offers read operations, allowing users to retrieve point of interest and amenity data.
- **Private API:** This API handles database operations such as creating, updating, and deleting point of interest and
  amenity data.

Both APIs adhere to the OpenAPI standard. We generate our API documentation using Swagger tools to ensure clear and
interactive representation. You can view our interactive API
documentation [here](https://api.dallasbymetro.com/swagger-ui/index.html).

## Hosting

We host our application on AWS. Detailed information about our hosting setup can be found in our
wiki [here](https://github.com/DartExplore/dallas-by-metro-backend/wiki/Hosting).

## Getting Involved

This project was initially developed to assist residents of Dallas in navigating the DART train system. However, the
code base has been structured in a way that should make it easily extendable to other transit systems.

Whether you're interested in adapting this project for another city or contributing to its current form, we welcome your
involvement! Feel free to fork the repository to create your own version, or reach out to us directly if you'd like to
contribute.
