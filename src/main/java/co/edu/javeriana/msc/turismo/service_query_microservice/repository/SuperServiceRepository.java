package co.edu.javeriana.msc.turismo.service_query_microservice.repository;

import co.edu.javeriana.msc.turismo.service_query_microservice.dtos.SuperService;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuperServiceRepository extends MongoRepository<SuperService, Long> {
}
