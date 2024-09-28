package co.edu.javeriana.msc.turismo.service_query_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationServiceResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import org.springframework.stereotype.Service;

@Service
public class AccommodationServiceMapper {

    public AccommodationServiceResponse toAccommodationServiceResponse(SuperService superService) {
        return new AccommodationServiceResponse(
                superService.id(),
                superService.createdBy(),
                superService.destination(),
                superService.name(),
                superService.description(),
                superService.unitValue(),
                superService.startDate(),
                superService.endDate(),
                superService.accommodationType(),
                superService.capacity()
        );
    }
}
