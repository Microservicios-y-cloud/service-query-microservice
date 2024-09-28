package co.edu.javeriana.msc.turismo.service_query_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.FoodServiceResponse;
import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceMapper {

    public FoodServiceResponse toFoodServiceResponse(SuperService superService) {
        return new FoodServiceResponse(
                superService.id(),
                superService.createdBy(),
                superService.destination(),
                superService.name(),
                superService.description(),
                superService.unitValue(),
                superService.startDate(),
                superService.endDate(),
                superService.foodType()
        );
    }
}
