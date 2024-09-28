package co.edu.javeriana.msc.turismo.service_query_microservice.services;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.FoodTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodTypeService {
    private final FoodTypeRepository repository;

    public FoodTypeResponse findById(Long serviceId) {
        return repository.findById(serviceId).orElseThrow(
                () -> new RuntimeException("Service not found")
        );
    }
    public List<FoodTypeResponse> findAll() {
        return repository.findAll();
    }
}
