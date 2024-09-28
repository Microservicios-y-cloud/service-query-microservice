package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.AccommodationTypeService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/services/AccommodationType")
@RequiredArgsConstructor
public class AccommodationTypeController {
    private final AccommodationTypeService AccommodationTypeService;

    @GetMapping("/{service-id}")
    public ResponseEntity<AccommodationTypeResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(AccommodationTypeService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<AccommodationTypeResponse>> findAll(){
        return ResponseEntity.ok(AccommodationTypeService.findAll());
    }
}
