const axios = require('axios');

const x_api_key = process.env.X_API_KEY;

const headers = {
    'x-api-key': x_api_key
};

const AMENITIES = [
    {amenityId: 1, amenity: "BIKE_PARKING"},
    {amenityId: 2, amenity: "OUTDOOR_SPACE"},
    {amenityId: 3, amenity: "INDOOR_SPACE"},
    {amenityId: 4, amenity: "WI_FI"},
    {amenityId: 5, amenity: "ALCOHOL"}
];

const POIS = [
    {
        poiId: 1,
        name: "Civil Pour", location: "8061 Walnut Hill Ln # 924, Dallas, TX 75231",
        walkingDistance: 3, picUrl: "coffee.png", type: "COFFEE_SHOP", stationId: 33,
        amenities: [AMENITIES[1], AMENITIES[2], AMENITIES[3], AMENITIES[4]]
    },
    {
        poiId: 2,
        name: "Dallas Museum of Art", location: "Dallas Museum of Art, 1717 N Harwood St, Dallas, TX 75201",
        walkingDistance: 9, picURL: "museum.png", type: "MUSEUM", stationId: 5,
        amenities: [AMENITIES[0], AMENITIES[2]]
    },
    {
        poiId: 3,
        name: "The Dallas World Aquarium", location: "The Dallas World Aquarium, 1801 N Griffin St, Dallas, TX 75202",
        walkingDistance: 6, picUrl: "aquarium.png", type: "AQUARIUM", stationId: 15,
        amenities: [AMENITIES[2]]
    },
    {
        poiId: 4,
        name: "AT&T Discovery District", location: "AT&T Discovery District, 308 S Akard St, Dallas, TX 75202",
        walkingDistance: 5, picUrl: "park.png", type: "PARK", stationId: 15,
        amenities: [AMENITIES[0], AMENITIES[1], AMENITIES[2]]
    },
    {
        poiId: 5,
        name: "American Airlines Center", location: "American Airlines, 2500 Victory Ave, Dallas, TX 75219",
        walkingDistance: 4, picUrl: "stadium.png", type: "STADIUM", stationId: 29,
        amenities: [AMENITIES[2]]
    }
];

POIS.map(
    (poi) => (
        axios.post('http://localhost:8080/api/private/poi', poi, {headers})
            .then(response =>
                console.log("POI " + poi.poiId + " inserted successfully.")
            )
            .catch(error =>
                console.log('Error:', error.message)
            )
    )
);
