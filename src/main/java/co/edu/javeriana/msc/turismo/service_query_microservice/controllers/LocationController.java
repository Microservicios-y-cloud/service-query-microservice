package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
