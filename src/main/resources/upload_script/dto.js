export class POI {
    constructor(poiId, name, location, walkingDistance,
        picUrl, type, amenities, stationId, stationName) {
      this.poiId = poiId;
      this.name = name;
      this.location = location;
      this.walkingDistance = walkingDistance;
      this.picUrl = picUrl;
      this.type = type;
      this.amenities = amenities;
      this.stationId = stationId;
      this.stationName = stationName;
    }
  }
  
export class Amenity {
  constructor(amenityId, amenity) {
    this.amenityId = amenityId;
    this.amenity = amenity;
  }
}
  