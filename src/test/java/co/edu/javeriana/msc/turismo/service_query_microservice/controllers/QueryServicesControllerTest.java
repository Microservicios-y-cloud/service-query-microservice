package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.LocationResponse;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.TransportTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.enums.ServiceType;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.LocationRepository;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.SuperServiceRepository;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.LocationService;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.ServiceService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureGraphQlTester
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class QueryServicesControllerTest {

    @Autowired
    private ServiceService queryService;

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


    // Crear el objeto SuperService con información inventada
    static final LocationResponse destination = new LocationResponse(
            1L,
            "Calle de Gran Vía, 1",
            40.4200,
            -3.7050,
            "España",
            "Madrid",
            "Centro"
    );

    static final LocationResponse origin = new LocationResponse(
            2L,
            "Carrer de Pau Claris, 30",
            41.3851,
            2.1734,
            "España",
            "Barcelona",
            "Eixample"
    );

    static final ServiceType serviceType = ServiceType.ACCOMMODATION;
    static final FoodTypeResponse foodType = new FoodTypeResponse(1L, "Vegetariana");
    static final AccommodationTypeResponse accommodationType = new AccommodationTypeResponse(1L, "Hotel de lujo");
    static final TransportTypeResponse transportType = new TransportTypeResponse(1L, "Autobús turístico");

    static final SuperService superServicemock1 = new SuperService(
            1L,
            "TravelAgency",
            destination,
            "Luxury Disney",
            "A guided luxury tour around the top attractions of Madrid",
            new BigDecimal("299.99"),
            LocalDateTime.of(2024, 12, 1, 9, 0),
            LocalDateTime.of(2024, 12, 7, 18, 0),
            serviceType,
            foodType,
            accommodationType,
            30,
            transportType,
            origin,
            "TravelWorld Ltd."
    );


    static final SuperService superServicemock2 = new SuperService(
            2L,
            "TravelCompany",
            destination,
            "Luxury Tour",
            "A guided luxury tour around the top attractions of Madrid",
            new BigDecimal("299.99"),
            LocalDateTime.of(2024, 12, 1, 9, 0),
            LocalDateTime.of(2024, 12, 7, 18, 0),
            serviceType,
            foodType,
            accommodationType,
            30,
            transportType,
            origin,
            "TravelWorld Ltd."
    );

    static final SuperService superServicemock3 = new SuperService(
            3L,
            "TravelCompany",
            destination,
            "Luxury Tour",
            "A guided luxury tour around the top attractions of Madrid",
            new BigDecimal("299.99"),
            LocalDateTime.of(2024, 12, 1, 9, 0),
            LocalDateTime.of(2024, 12, 7, 18, 0),
            serviceType,
            foodType,
            accommodationType,
            30,
            transportType,
            origin,
            "Disney"
    );

    static KafkaConsumer<Object, Object> mockKafkaConsumer;

    static void createMockKafkaConsumer() {
        String groupId_1 = "service-group";
        String groupId_2 = "service-type-group";
        String groupId_3 = "my-consumer-group";
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_1);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_2);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId_3);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "co.edu.javeriana.msc.*");
        mockKafkaConsumer = new KafkaConsumer<>(properties, new JsonDeserializer<>(), new JsonDeserializer<>());
        mockKafkaConsumer.subscribe(List.of("contentsQueue"));
    }

    @BeforeAll
    static void beforeAll(@Autowired SuperServiceRepository superServiceRepository) {
        kafka.start();
        createMockKafkaConsumer();
        mongo.start();
        superServiceRepository.save(superServicemock1);
        superServiceRepository.save(superServicemock2);
        superServiceRepository.save(superServicemock3);

    }
    @AfterAll
    static void afterAll() {
        mongo.stop();
        kafka.stop();
    }

    @Test
    @Order(1)
    void servicesByKeyword() {
        List<SuperService> services = queryService.findAllByKeyword("España");
        assertThat(services).isNotEmpty();
        assertThat(services).hasSize(3);
    }
}