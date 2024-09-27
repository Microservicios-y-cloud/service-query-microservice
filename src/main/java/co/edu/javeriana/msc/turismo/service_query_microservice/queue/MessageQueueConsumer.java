package co.edu.javeriana.msc.turismo.service_query_microservice.queue;

import co.edu.javeriana.msc.turismo.service_query_microservice.dtos.SuperService;
import co.edu.javeriana.msc.turismo.service_query_microservice.dtos.SuperServiceDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.SuperServiceRepository;
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
}
