package co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue;

import jakarta.validation.constraints.NotNull;

public record ServiceType(
        @NotNull(message = "The id of the type of service is required")
        Long id,
        @NotNull(message = "The name of the type of service is required")
        String name,
        @NotNull(message = "The type of the type of service is required")
        co.edu.javeriana.msc.turismo.service_query_microservice.enums.ServiceType type
) {
}
