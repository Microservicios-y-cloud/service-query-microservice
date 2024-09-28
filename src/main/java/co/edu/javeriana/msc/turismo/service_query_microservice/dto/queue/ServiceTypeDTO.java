package co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue;

import co.edu.javeriana.msc.turismo.service_query_microservice.enums.CRUDEventType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceTypeDTO implements Serializable {
    private LocalDateTime dateTime;
    private CRUDEventType eventType;
    private ServiceType type;
}
