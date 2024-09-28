package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.FoodTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.FoodType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceMapper {
    public FoodService toFoodService(FoodServiceRequest request) {
        return FoodService.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .unitValue(request.unitValue())
                .destination(
                        Location.builder()
                                .id(request.destination().id())
                                .address(request.destination().address())
                                .latitude(request.destination().latitude())
                                .longitude(request.destination().longitude())
                                .country(request.destination().country())
                                .city(request.destination().city())
                                .municipality(request.destination().municipality())
                                .build())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .createdBy(request.supplierId())
                .foodType(
                        FoodType.builder()
                                .id(request.foodType().foodTypeId())
                                .name(request.foodType().name())
                                .build()
                )
                .build();
    }

    public FoodServiceResponse toFoodServiceResponse(FoodService foodService) {
        return new FoodServiceResponse(
                new ServiceResponse(
                        foodService.getId(),
                        foodService.getName(),
                        foodService.getDescription(),
                        foodService.getUnitValue(),
                        foodService.getDestination().getId(),
                        foodService.getDestination().getCountry(),
                        foodService.getDestination().getCity(),
                        foodService.getStartDate(),
                        foodService.getEndDate(),
                        foodService.getCreatedBy()
                ),
                new FoodTypeResponse(
                        foodService.getFoodType().getId(),
                        foodService.getFoodType().getName()
                )
        );
    }
}
