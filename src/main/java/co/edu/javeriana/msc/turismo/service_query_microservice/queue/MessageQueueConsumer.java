package co.edu.javeriana.msc.turismo.service_query_microservice.queue;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.LocationDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.ServiceTypeDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.ProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import java.util.function.Consumer;


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
            try{
                log.info("Received message: {}", message);
                log.info("Payload: {}", message.getPayload());
                //hacemos un switch case en caso de que sea CREATE, UPDATE, O DELETE
                switch (message.getPayload().getEventType()) {
                    case CREATE -> {
                        repository.save(message.getPayload().getSuperService());
                        log.info("Service saved: {}", message.getPayload().getSuperService());
                    }
                    case UPDATE -> {
                        var serviceToUpdate = repository.findById(message.getPayload().getSuperService().id()).orElse(null);
                        if (serviceToUpdate != null) {
                            repository.save(message.getPayload().getSuperService());
                            log.info("Service updated: {}", message.getPayload().getSuperService().id());
                        } else {
                            log.error("Service not found: {}", message.getPayload().getSuperService().id());
                            throw new EntityNotFoundException("Service not found: " + message.getPayload().getSuperService().id());
                        }
                    }
                    case DELETE -> {
                        repository.deleteById(message.getPayload().getSuperService().id());
                        log.info("Service deleted: {}", message.getPayload().getSuperService().id());
                    }
                    default -> {
                        log.error("Invalid action: {}", message.getPayload().getEventType());
                        throw new IllegalArgumentException("Invalid action: " + message.getPayload().getEventType());
                    }
                }
            } catch (Exception e) {
                log.error("Error processing message: {}", e.getMessage());
                throw new ProcessingException("Error processing SuperService message. Message received but could not be processed.", e);
            }
        };
    }

    @Bean
    Consumer<Message<ServiceTypeDTO>> receiveServiceType() {
        return message -> {
            try{
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
            } catch (Exception e) {
                log.error("Error processing message: {}", e.getMessage());
                throw new ProcessingException("Error processing ServiceType message. Message received but could not be processed.", e);
            }
        };

    }

    @Bean
    Consumer<Message<LocationDTO>> receiveLocation() {
        return message -> {
            try{
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
            } catch (Exception e) {
                log.error("Error processing message: {}", e.getMessage());
                throw new ProcessingException("Error processing Location message. Message received but could not be processed.", e);
            }
        };
    }
}
