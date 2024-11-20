package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.repository.AccommodationTypeRepository;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.AccommodationTypeService;

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
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureGraphQlTester
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
class AccommodationTypeControllerTest {

    @Autowired
    private AccommodationTypeService accommodationTypeService;

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
    static final AccommodationTypeResponse mockAccomodationService1 = new AccommodationTypeResponse(1L, "accommodationIdTest1");
    static final AccommodationTypeResponse mockAccomodationService2 = new AccommodationTypeResponse(2L, "accommodationIdTest2");

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
    static void beforeAll(@Autowired AccommodationTypeRepository accomodationTypeRepository) {
        kafka.start();
        createMockKafkaConsumer();
        mongo.start();
        accomodationTypeRepository.save(mockAccomodationService1);
        accomodationTypeRepository.save(mockAccomodationService2);

    }
    @AfterAll
    static void afterAll() {
        mongo.stop();
        kafka.stop();
    }

    @Test
    @Order(1)
    void getService() throws Exception {
        //Buscar si en la BDD está el servicio con id 1
        if(accommodationTypeService.findById(1L) != null){
            assertThat(accommodationTypeService.findById(1L)).isEqualTo(mockAccomodationService1);
        }
    }

    @Test
    @Order(1)
    void findAll() throws Exception {
        //Buscar si en la BDD estan los servicios
        if(accommodationTypeService.findAll() != null){
            assertThat(accommodationTypeService.findAll()).contains(mockAccomodationService1, mockAccomodationService2);
        }
    }
}