package com.dallasbymetro.backend.repository;

import com.dallasbymetro.backend.entity.Amenity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AmenityRepository extends CrudRepository<Amenity, Long> {
    @Query("SELECT a FROM Amenity a")
    List<Amenity> findAllAmenities();

    @Query("SELECT a FROM Amenity a WHERE a.amenityId IN :ids")
    List<Amenity> findAllAmenitiesById(@Param("ids") List<Long> ids);
}
