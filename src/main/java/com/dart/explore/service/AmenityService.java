package com.dart.explore.service;

import com.dart.explore.dto.AmenityDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Save to the database
        Amenity savedAmenity = amenityRepository.save(amenity);
        // Convert back to DTO and return
        return AmenityDTO.prepareAmenityDTO(savedAmenity);
    }

    public String deleteAmenity(AmenityDTO amenityDTO) {
        // Fetch the amenity from the database
        Optional<Amenity> optionalAmenity = amenityRepository.findById(amenityDTO.getAmenityId());

        if (optionalAmenity.isEmpty()) {
            return "Amenity with id: " + amenityDTO.getAmenityId() + " does not exist";
        }

        // Store the amenity name before deletion
        String amenityName = optionalAmenity.get().getAmenity();

        // Delete the entity from the database
        amenityRepository.deleteById(amenityDTO.getAmenityId());

        // Check if the entity still exists in the database
        if (amenityRepository.existsById(amenityDTO.getAmenityId())) {
            return "Failed to delete amenity: " + amenityName;
        } else {
            return "Amenity: " + amenityName + " successfully deleted";
        }
    }


}
