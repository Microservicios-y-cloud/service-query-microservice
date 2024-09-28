package co.edu.javeriana.msc.turismo.service_publication_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.services.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping("/{service-id}")
    public ResponseEntity<ServiceResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(serviceService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> findAll(){
        return ResponseEntity.ok(serviceService.findAll());
    }

    @GetMapping("/supplier/{supplier-id}")
    public ResponseEntity<List<ServiceResponse>> findAllBySupplier(
            @PathVariable("supplier-id") String supplierId) {
        return ResponseEntity.ok(serviceService.findAllBySupplier(supplierId));
    }
}
