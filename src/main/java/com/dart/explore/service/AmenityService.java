package com.dart.explore.service;

import com.dart.explore.dto.AmenityDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.exception.DartExploreException;
import com.dart.explore.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    @Autowired
    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public AmenityDTO addAmenity(AmenityDTO amenityDTO) {
        // Convert DTO to Entity using prepareEntity method
        Amenity amenity = AmenityDTO.prepareAmenityEntity(amenityDTO);
        Amenity savedAmenity = amenityRepository.save(amenity);
        return AmenityDTO.prepareAmenityDTO(savedAmenity);
    }

    public AmenityDTO updateAmenity(AmenityDTO amenityDTO) throws DartExploreException {
        // Check if the Amenity exists in the database
        Optional<Amenity> optionalAmenity = amenityRepository.findById(amenityDTO.getAmenityId());

        if (optionalAmenity.isEmpty()) {
            throw new DartExploreException("Amenity with id: " + amenityDTO.getAmenityId() + " does not exist");
        }

        Amenity amenityEntity = optionalAmenity.get();
        amenityEntity.setAmenity(amenityDTO.getAmenity());
        Amenity updatedAmenity = amenityRepository.save(amenityEntity);
        return AmenityDTO.prepareAmenityDTO(updatedAmenity);
    }

    public void deleteAmenity(AmenityDTO amenityDTO) throws DartExploreException {
        // Fetch the amenity from the database
        Optional<Amenity> optionalAmenity = amenityRepository.findById(amenityDTO.getAmenityId());

        if (optionalAmenity.isEmpty()) {
            throw new DartExploreException("Amenity with id: " + amenityDTO.getAmenityId() + " does not exist");
        }

        amenityRepository.deleteById(amenityDTO.getAmenityId());
    }

    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAllAmenities();
    }
}
