package com.api.estacionamiento.repositories;

import com.api.estacionamiento.models.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {

  boolean existsByLicensePlateCar(String licensePlateCar);
  boolean existsByParkingSpotNumber(String parkingSpotNumber);
  boolean existsByApartment(String apartment);
  boolean existsByBlock(String block);
}
