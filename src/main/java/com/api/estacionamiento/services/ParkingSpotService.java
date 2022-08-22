package com.api.estacionamiento.services;

import com.api.estacionamiento.models.ParkingSpotModel;
import com.api.estacionamiento.repositories.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

  final ParkingSpotRepository parkingSpotRepository;

  public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
    this.parkingSpotRepository = parkingSpotRepository;
  }

  @Transactional
  public Object save(ParkingSpotModel parkingSpotModel) {
    return parkingSpotRepository.save(parkingSpotModel);
  }

  public boolean existsByLicensePlateCar(String licensePlateCar) {
    return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
  }

  public boolean existsByParkingSpotNumber(String parkingSpotNumber){
    return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
  }

  public boolean existsByApartment(String apartment){
    return parkingSpotRepository.existsByApartment(apartment);
  }

  public boolean existsByBlock(String block){
    return parkingSpotRepository.existsByBlock(block);
  }

  public List<ParkingSpotModel> findAll() {
    return parkingSpotRepository.findAll();
  }

  public Optional<ParkingSpotModel> finById(UUID id) {
    return parkingSpotRepository.findById(id);
  }
}
