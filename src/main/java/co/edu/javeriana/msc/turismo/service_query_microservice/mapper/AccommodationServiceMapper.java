package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceRequest;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.AccommodationTypeResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.ServiceResponse;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationService;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.AccommodationType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import org.springframework.stereotype.Service;

@Service
public class AccommodationServiceMapper {
        public AccommodationService toAccomodationService(AccommodationServiceRequest request) {
                return AccommodationService.builder()
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
                                .type(
                                                AccommodationType.builder()
                                                                .id(request.accommodationType().accomodationTypeId())
                                                                .name(request.accommodationType().name())
                                                                .build()
                                )
                                .capacity(request.capacity())
                                .build();
        }

        public AccommodationServiceResponse toAccommodationServiceResponse(AccommodationService accommodationService) {
                return new AccommodationServiceResponse(
                                new ServiceResponse(
                                                accommodationService.getId(),
                                                accommodationService.getName(),
                                                accommodationService.getDescription(),
                                                accommodationService.getUnitValue(),
                                                accommodationService.getDestination().getId(),
                                                accommodationService.getDestination().getCountry(),
                                                accommodationService.getDestination().getCity(),
                                                accommodationService.getStartDate(),
                                                accommodationService.getEndDate(),
                                                accommodationService.getCreatedBy()
                                ),
                                new AccommodationTypeResponse(
                                                accommodationService.getType().getId(),
                                                accommodationService.getType().getName()
                                ),
                                accommodationService.getCapacity());
        }
}
