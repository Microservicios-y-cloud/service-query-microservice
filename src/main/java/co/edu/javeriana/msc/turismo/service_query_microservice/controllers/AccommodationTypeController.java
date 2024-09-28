package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.AccommodationTypeService;
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
@RequestMapping("/types/accommodation")
@RequiredArgsConstructor
public class AccommodationTypeController {
    private final co.edu.javeriana.msc.turismo.service_query_microservice.services.AccommodationTypeService AccommodationTypeService;

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
