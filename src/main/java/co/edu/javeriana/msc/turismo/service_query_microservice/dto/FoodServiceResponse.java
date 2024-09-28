package co.edu.javeriana.msc.turismo.service_query_microservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FoodServiceResponse(
        Long id,
        String createdBy,
        LocationResponse destination,
        String name,
        String description,
        BigDecimal unitValue,
        LocalDateTime startDate,
        LocalDateTime endDate,
        FoodTypeResponse foodType
) {
}
