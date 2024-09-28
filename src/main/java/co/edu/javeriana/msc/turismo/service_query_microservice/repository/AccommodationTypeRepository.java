package co.edu.javeriana.msc.turismo.service_query_microservice.repository;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.LocationResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccommodationTypeRepository extends MongoRepository<AccommodationTypeResponse, Long> {
}
