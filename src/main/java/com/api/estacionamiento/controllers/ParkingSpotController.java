package com.api.estacionamiento.controllers;

import com.api.estacionamiento.dtos.ParkingSpotDto;
import com.api.estacionamiento.models.ParkingSpotModel;
import com.api.estacionamiento.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

  final ParkingSpotService parkingSpotService;
  //crear un constructor con la importacion de service es similar a utilizar @Autowired
  public ParkingSpotController(ParkingSpotService parkingSpotService) {
    this.parkingSpotService = parkingSpotService;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping()
  public ResponseEntity<Object> saveParking(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
    // Validaciones
    if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
    }
    if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot is already in use!");
    }
    if(parkingSpotService.existsByApartment(parkingSpotDto.getApartment())){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Conflict: Parking Spot already registered for this apartment");
    }
    if(parkingSpotService.existsByBlock(parkingSpotDto.getBlock())){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Conflict: Parking Spot already registered for this block!");
    }
    //--
    // Se instancia la clase, se copia los datos del dto al objeto, se agrega el date al objeto y se guarda y envia msg
    var parkingSpotModel = new ParkingSpotModel();
    BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
    parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
    return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @GetMapping
  public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpot(){
    return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @GetMapping("/{id}")
  public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id")UUID id){
    Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.finById(id);
    if(!parkingSpotModelOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No exist the parkingSpot!!!");
    }else{
      return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id")UUID id){
    Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.finById(id);
    if(!parkingSpotModelOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The ParkingSpot no exist!!");
    }
    parkingSpotService.delete(parkingSpotModelOptional.get());
    return ResponseEntity.status(HttpStatus.OK).body("The ParkingSpot was eliminated");

  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                  @RequestBody ParkingSpotDto parkingSpotDto){
    Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.finById(id);
    if(!parkingSpotModelOptional.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id no existe");
    }
    var parkingSpotModel = new ParkingSpotModel();
    BeanUtils.copyProperties(parkingSpotDto,parkingSpotModel);
    parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
    parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
    return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
  }



}
