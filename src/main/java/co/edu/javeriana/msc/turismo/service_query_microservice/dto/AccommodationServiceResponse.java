package co.edu.javeriana.msc.turismo.service_publication_microservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AccommodationServiceResponse(
        ServiceResponse serviceResponse,
        AccommodationTypeResponse accommodationType,
        Integer capacity
) {
}
