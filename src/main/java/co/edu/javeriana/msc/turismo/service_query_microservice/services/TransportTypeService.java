package co.edu.javeriana.msc.turismo.service_query_microservice.services;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.TransportTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransportTypeService {
    private final TransportTypeRepository repository;

    public TransportTypeResponse findById(Long transportTypeId) {
        return repository.findById(transportTypeId).orElseThrow(
                () -> new RuntimeException("TransportType not found")
        );
    }
    public List<TransportTypeResponse> findAll() {
        return repository.findAll();
    }
}
