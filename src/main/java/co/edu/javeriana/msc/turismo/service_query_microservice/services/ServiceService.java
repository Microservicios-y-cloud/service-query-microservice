package co.edu.javeriana.msc.turismo.service_query_microservice.services;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import co.edu.javeriana.msc.turismo.service_query_microservice.enums.ServiceType;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.SuperServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceService {
    private final SuperServiceRepository repository;
    public SuperService findById(Long serviceId) {
        return repository.findById(serviceId).orElseThrow(
                () -> new RuntimeException("Service not found")
        );
    }

    public List<SuperService> findAll() {
        return repository.findAll();
    }

    public List<SuperService> findAllBySupplier(String supplierId) {
        return repository.findAllByCreatedBy(supplierId);
    }

    public List<SuperService> findAllByType(String type) {
        if(type.equals(ServiceType.ACCOMMODATION.name())) {
            return repository.findAllByServiceType(ServiceType.ACCOMMODATION);
        }
        else if(type.equals(ServiceType.TRANSPORTATION.name())) {
            return repository.findAllByServiceType(ServiceType.TRANSPORTATION);
        }
        else if(type.equals(ServiceType.FOOD.name())) {
            return repository.findAllByServiceType(ServiceType.FOOD);
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<SuperService> findAllByKeyword(String keyword) {
        List<SuperService> services = new ArrayList<>();

        // Agregar la lógica para verificar si el keyword es un valor numérico (BigDecimal)
        try {
            BigDecimal unitValue = new BigDecimal(keyword);
            services.addAll(repository.findAllByUnitValueKeyword(unitValue));
        } catch (NumberFormatException | NullPointerException e) {
            // Si no es numérico o es nulo, simplemente ignoramos esta búsqueda
        }

        services.addAll(repository.findAllByKeyword(keyword));

        log.info("Services found by keyword: {}", services.size());
        return services;
    }
}
