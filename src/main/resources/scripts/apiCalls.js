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
        amenities: ["OUTDOOR_SPACE", "INDOOR_SPACE", "WI_FI", "ALCOHOL"]
    },
    {
        poiId: 2,
        name: "Dallas Museum of Art", location: "Dallas Museum of Art, 1717 N Harwood St, Dallas, TX 75201",
        walkingDistance: 9, picURL: "museum.png", type: "MUSEUM", stationId: 5,
        amenities: ["BIKE_PARKING", "INDOOR_SPACE"]
    },
    {
        poiId: 3,
        name: "The Dallas World Aquarium with a longer name",
        location: "The Dallas World Aquarium, 1801 N Griffin St, Dallas, TX 75202",
        walkingDistance: 6,
        picUrl: "aquarium.png",
        type: "AQUARIUM",
        stationId: 50,
        amenities: ["INDOOR_SPACE"]
    },
    {
        poiId: 4,
        name: "AT&T Discovery District", location: "AT&T Discovery District, 308 S Akard St, Dallas, TX 75202",
        walkingDistance: 5, picUrl: "park.png", type: "PARK", stationId: 15,
        amenities: ["BIKE_PARKING", "OUTDOOR_SPACE", "INDOOR_SPACE"]
    },
    {
        poiId: 5,
        name: "American Airlines Center", location: "American Airlines, 2500 Victory Ave, Dallas, TX 75219",
        walkingDistance: 4, picUrl: "stadium.png", type: "STADIUM", stationId: 29,
        amenities: ["INDOOR_SPACE"]
    }
];

const addAmenities = async (amenities) => {
    for (const amenity of amenities) {
        await axios.post('http://localhost:8080/api/private/amenity', amenity, {headers})
            .then(response => console.log("Amenity " + response.data.amenityId + " inserted successfully."))
            .catch(error => console.log(error.response.data));
    }
}

const getAllAmenities = async () => {
    const response = await axios.get('http://localhost:8080/api/public/amenities', {headers});
    return response.data;
};

const addPOIs = async (pois) => {
    for (const poi of pois) {
        await axios.post('http://localhost:8080/api/private/poi', poi, {headers})
            .then(response => console.log("POI " + poi.poiId + " inserted successfully."))
            .catch(error => console.log(error.response.data));
    }
}

// Execution
(async () => {
    await addAmenities(AMENITIES);
    const amenitiesFromAPI = await getAllAmenities();
    const amenitiesByName = amenitiesFromAPI.reduce((acc, curr) => {
        acc[curr.amenity] = curr;
        return acc;
    }, {});

    POIS.forEach(poi => {
        poi.amenities = poi.amenities.map(a => amenitiesByName[a]);
    });

    await addPOIs(POIS);
})();
