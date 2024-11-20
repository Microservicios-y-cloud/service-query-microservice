package co.edu.javeriana.msc.turismo.service_query_microservice.queue;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.LocationResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.LocationDTO;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.LocationRepository;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import com.google.gson.Gson;

@SpringBootTest
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
class MessageQueueConsumerTest {

    @Autowired
    private LocationRepository locationRepository;

    @Container
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.6");

    @Container
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongo::getHost);
        registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    private static KafkaTemplate<String, Object> kafkaTemplate;

    static void createKafkaProducer() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        configProps.put(ProducerConfig.ACKS_CONFIG, "1");

        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }



    static final LocationResponse locationResponse = new LocationResponse(1L, "testAddress",
            10.2, 20.3, "testCountry", "testCity", "testMun");

    @BeforeAll
    static void setup() {
        mongo.start();
        kafka.start();

        createKafkaProducer();

        Gson gson = new Gson();
        LocationDTO locationDTO = new LocationDTO();
        kafkaTemplate.send("service-type-group", gson.toJson(locationDTO));
    }

    @AfterAll
    static void teardown() {
        kafka.stop();
        mongo.stop();
    }

    @Test
    @Order(1)
    void receiveLocation() {
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            assertThat(locationRepository.findById(1L)).isPresent();
        });
    }
}