package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/services/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/{service-id}")
    public ResponseEntity<LocationResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(locationService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> findAll(){
        return ResponseEntity.ok(locationService.findAll());
    }
}
