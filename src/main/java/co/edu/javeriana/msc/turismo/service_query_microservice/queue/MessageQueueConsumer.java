package co.edu.javeriana.msc.turismo.service_query_microservice.queue;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.LocationDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.ServiceTypeDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.enums.CRUDEventType;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import co.edu.javeriana.msc.turismo.service_query_microservice.enums.ServiceType;

import static co.edu.javeriana.msc.turismo.service_query_microservice.enums.ServiceType.*;

@Slf4j
@Configuration
@AllArgsConstructor
public class MessageQueueConsumer {
    private final SuperServiceRepository repository;
    private AccommodationTypeRepository accommodationTypeRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final TransportTypeRepository transportTypeRepository;
    private final LocationRepository locationRepository;

    @Bean
    Consumer<Message<SuperServiceDTO>> receiveMessage() {
        return message -> {
            log.info("Received message: {}", message);
            log.info("Payload: {}", message.getPayload());
            //hacemos un switch case en caso de que sea CREATE, UPDATE, O DELETE
            switch (message.getPayload().getEventType()) {
                case CREATE:
                    repository.save(message.getPayload().getSuperService());
                    log.info("Service saved: {}", message.getPayload().getSuperService());
                    break;
//                case UPDATE:
//                    repository.save(new SuperService(message.getPayload().getService()));
//                    break;
//                case DELETE:
//                    repository.deleteById(message.getPayload().getService().getId());
//                    break;
                default:
                    log.error("Invalid action: {}", message.getPayload().getEventType());
                    break;
            }
        };
    }

    @Bean
    Consumer<Message<ServiceTypeDTO>> receiveServiceType() {
        return message -> {
            log.info("Received ServiceType: {}", message);
            log.info("Payload: {}", message.getPayload());

            switch (message.getPayload().getEventType()) {
                case CREATE:
                    switch (message.getPayload().getType().type()) {
                        case ACCOMMODATION -> {
                            accommodationTypeRepository.save(new AccommodationTypeResponse(
                                    message.getPayload().getType().id(),
                                    message.getPayload().getType().name()
                            ));
                            log.info("Accommodation saved: {}", message.getPayload().getType());
                        }
                        case FOOD -> {
                            foodTypeRepository.save(
                                    new FoodTypeResponse(
                                            message.getPayload().getType().id(),
                                            message.getPayload().getType().name()
                                    )
                            );
                            log.info("Food saved: {}", message.getPayload().getType());
                        }
                        case TRANSPORTATION -> {
                            transportTypeRepository.save(new TransportTypeResponse(
                                    message.getPayload().getType().id(),
                                    message.getPayload().getType().name()
                            ));
                            log.info("Transport saved: {}", message.getPayload().getType());
                        }
                    }
                case UPDATE:
                    System.out.println("UPDATE");
            }
        };

    }

    @Bean
    Consumer<Message<LocationDTO>> receiveLocation() {
        return message -> {
            log.info("Received Location: {}", message);
            log.info("Payload: {}", message.getPayload());
            switch (message.getPayload().getEventType()) {
                case CREATE:
                    locationRepository.save(new LocationResponse(
                            message.getPayload().getLocation().id(),
                            message.getPayload().getLocation().address(),
                            message.getPayload().getLocation().latitude(),
                            message.getPayload().getLocation().longitude(),
                            message.getPayload().getLocation().country(),
                            message.getPayload().getLocation().city(),
                            message.getPayload().getLocation().municipality()
                    ));
                    log.info("Location saved: {}", message.getPayload().getLocation());
                    break;
            }
        };
    }
}
