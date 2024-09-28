package co.edu.javeriana.msc.turismo.service_publication_microservice.queue.dto;

import jakarta.validation.constraints.NotNull;

public record ServiceType(
        @NotNull(message = "The id of the type of service is required")
        Long id,
        @NotNull(message = "The name of the type of service is required")
        String name,
        @NotNull(message = "The type of the type of service is required")
        co.edu.javeriana.msc.turismo.service_publication_microservice.enums.ServiceType type
) {
}
