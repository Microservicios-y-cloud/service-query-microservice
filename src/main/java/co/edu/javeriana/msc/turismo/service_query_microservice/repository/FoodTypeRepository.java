package co.edu.javeriana.msc.turismo.service_query_microservice.repository;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.FoodTypeResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodTypeRepository extends MongoRepository<FoodTypeResponse, Long> {
}
