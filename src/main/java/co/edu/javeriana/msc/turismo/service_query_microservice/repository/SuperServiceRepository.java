package co.edu.javeriana.msc.turismo.service_query_microservice.repository;

import co.edu.javeriana.msc.turismo.service_query_microservice.enums.ServiceType;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface SuperServiceRepository extends MongoRepository<SuperService, Long> {
    List<SuperService> findAllByCreatedBy(String supplierId);

    List<SuperService> findAllByServiceType(@NotNull ServiceType serviceType);

    @Query("{'unitValue': ?0}")
    List<SuperService> findAllByUnitValueKeyword(BigDecimal unitValue);

    @Query("{'$or': [" +
            "{'name': {$regex: ?0, $options: 'i'}}, " +
            "{'description': {$regex: ?0, $options: 'i'}}, " +
            "{'destination.address': {$regex: ?0, $options: 'i'}}, " +
            "{'destination.city': {$regex: ?0, $options: 'i'}}, " +
            "{'destination.country': {$regex: ?0, $options: 'i'}}, " +
            "{'destination.municipality': {$regex: ?0, $options: 'i'}}, " +
            "{'origin.address': {$regex: ?0, $options: 'i'}}, " +
            "{'origin.city': {$regex: ?0, $options: 'i'}}, " +
            "{'origin.country': {$regex: ?0, $options: 'i'}}, " +
            "{'origin.municipality': {$regex: ?0, $options: 'i'}}, " +
            "{'serviceType': {$regex: ?0, $options: 'i'}}, " +
            "{'foodType.name': {$regex: ?0, $options: 'i'}}, " +
            "{'accommodationType.name': {$regex: ?0, $options: 'i'}}, " +
            "{'transportationType.name': {$regex: ?0, $options: 'i'}} " +
            "]}")
    List<SuperService> findAllByKeyword(String keyword);
}
