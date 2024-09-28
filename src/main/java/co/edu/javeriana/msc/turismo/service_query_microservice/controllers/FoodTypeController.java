package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.FoodTypeService;
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
@RequestMapping("/types/food")
@RequiredArgsConstructor
public class FoodTypeController {
    private final co.edu.javeriana.msc.turismo.service_query_microservice.services.FoodTypeService FoodTypeService;

    @GetMapping("/{service-id}")
    public ResponseEntity<FoodTypeResponse> getService(
            @PathVariable("service-id") Long serviceId) {
        return ResponseEntity.ok(FoodTypeService.findById(serviceId));
    }

    @GetMapping
    public ResponseEntity<List<FoodTypeResponse>> findAll(){
        return ResponseEntity.ok(FoodTypeService.findAll());
    }
}
