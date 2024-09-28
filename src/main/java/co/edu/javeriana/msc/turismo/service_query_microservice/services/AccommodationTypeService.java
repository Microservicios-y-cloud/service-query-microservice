package co.edu.javeriana.msc.turismo.service_query_microservice.services;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.AccommodationTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccommodationTypeService {
    private final AccommodationTypeRepository repository;

    public AccommodationTypeResponse findById(Long serviceId) {
        return repository.findById(serviceId).orElseThrow(
                () -> new RuntimeException("Service not found")
        );
    }

    public List<AccommodationTypeResponse> findAll() {
        return repository.findAll();
    }
}
