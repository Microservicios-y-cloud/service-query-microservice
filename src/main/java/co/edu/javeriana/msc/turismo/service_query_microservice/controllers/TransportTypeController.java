package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;


import co.edu.javeriana.msc.turismo.service_query_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.TransportTypeService;
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
@RequestMapping("/types/transport")
@RequiredArgsConstructor
public class TransportTypeController {
    private final TransportTypeService transportTypeService;

    @GetMapping("/{service-id}")
    public ResponseEntity<TransportTypeResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(transportTypeService.findById(serviceId));
    }
    @GetMapping
    public ResponseEntity<List<TransportTypeResponse>> findAll(){
        return ResponseEntity.ok(transportTypeService.findAll());
    }
}
