package co.edu.javeriana.msc.turismo.service_query_microservice.services;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {
    private final LocationRepository repository;

    public LocationResponse findById(Long locationId) {
        return repository.findById(locationId).orElseThrow(
                () -> new RuntimeException("Location not found")
        );
    }
    public List<LocationResponse> findAll() {
        return repository.findAll();
    }
}
