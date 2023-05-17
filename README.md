# dart-explore-backend

![Train going through Downtown.](https://texashighways.com/wp-content/uploads/2015/06/images_dart2.jpg)

Backend for DART Explore using Spring Boot. Database implemented in Postgres. We expose the backend through a RESTful API.

## How to Run
Simply clone the repository and open the project in your IDE of choice. If application cannot run, ensure you have a recent version of Java and run a Maven update.

## Database

![Database diagram.](https://user-images.githubusercontent.com/85081861/238772266-278600a4-21c6-44f1-af4c-049acbb6e44a.png)

## API
The current API layout is as follows:

- **/api/poi** &rarr; List getPOIs(List amenities)  
- **/api/station/{line}**  &rarr; List getStationsByLine(StationColor color)
- **/api/poi/{station}**  &rarr; List getPOIsByStation(String stationName)
- **/api/poi/{station}** &rarr; List getPOIsAtStation(String stationName, List amenities)
