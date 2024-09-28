package co.edu.javeriana.msc.turismo.service_query_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.TransportationServiceResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import org.springframework.stereotype.Service;

@Service
public class TransportationServiceMapper {

    public TransportationServiceResponse toTransportationServiceResponse(SuperService superService) {
        return new TransportationServiceResponse(
                superService.id(),
                superService.createdBy(),
                superService.destination(),
                superService.name(),
                superService.description(),
                superService.unitValue(),
                superService.startDate(),
                superService.endDate(),
                superService.origin(),
                superService.transportationType(),
                superService.company()
        );
    }
}
