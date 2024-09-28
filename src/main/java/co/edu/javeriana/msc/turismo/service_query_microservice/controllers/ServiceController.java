package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.ServiceService;
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
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping("/{service-id}")
    public ResponseEntity<SuperService> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(serviceService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<SuperService>> findAll(){
        return ResponseEntity.ok(serviceService.findAll());
    }

    @GetMapping("/supplier/{supplier-id}")
    public ResponseEntity<List<SuperService>> findAllBySupplier(
            @PathVariable("supplier-id") String supplierId) {
        return ResponseEntity.ok(serviceService.findAllBySupplier(supplierId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<SuperService>> findAllByType(
            @PathVariable("type") String type) {
        return ResponseEntity.ok(serviceService.findAllByType(type));
    }
}
