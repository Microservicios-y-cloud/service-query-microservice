package co.edu.javeriana.msc.turismo.service_query_microservice.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccommodationServiceResponse(
        Long id,
        String createdBy,
        LocationResponse destination,
        String name,
        String description,
        BigDecimal unitValue,
        LocalDateTime startDate,
        LocalDateTime endDate,
        AccommodationTypeResponse accommodationType,
        Integer capacity
) {
}
