package co.edu.javeriana.msc.turismo.service_publication_microservice.mapper;

import co.edu.javeriana.msc.turismo.service_publication_microservice.dto.*;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.Location;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportType;
import co.edu.javeriana.msc.turismo.service_publication_microservice.model.TransportationService;
import org.springframework.stereotype.Service;

@Service
public class TransportationServiceMapper {
    public TransportationService toTransportationService(TransportationServiceRequest request) {
        return TransportationService.builder()
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
                                .build()
                )
                .startDate(request.startDate())
                .endDate(request.endDate())
                .createdBy(request.supplierId())
                .transportType(
                        TransportType.builder()
                                .id(request.transportType().transportTypeId())
                                .name(request.transportType().name())
                                .build()
                )
                .company(request.company())
                .origin(
                        Location.builder()
                                .id(request.origin().id())
                                .address(request.origin().address())
                                .latitude(request.origin().latitude())
                                .longitude(request.origin().longitude())
                                .country(request.origin().country())
                                .city(request.origin().city())
                                .municipality(request.origin().municipality())
                                .build()
                )
                .build();
    }

    public TransportationServiceResponse toTransportationServiceResponse(TransportationService transportationService) {
        return new TransportationServiceResponse(
                new ServiceResponse(
                        transportationService.getId(),
                        transportationService.getName(),
                        transportationService.getDescription(),
                        transportationService.getUnitValue(),
                        transportationService.getDestination().getId(),
                        transportationService.getDestination().getCountry(),
                        transportationService.getDestination().getCity(),
                        transportationService.getStartDate(),
                        transportationService.getEndDate(),
                        transportationService.getCreatedBy()
                ),
                new LocationResponse(
                        transportationService.getOrigin().getId(),
                        transportationService.getOrigin().getAddress(),
                        transportationService.getOrigin().getLatitude(),
                        transportationService.getOrigin().getLongitude(),
                        transportationService.getOrigin().getCountry(),
                        transportationService.getOrigin().getCity(),
                        transportationService.getOrigin().getMunicipality()
                ),
                new TransportTypeResponse(
                        transportationService.getTransportType().getId(),
                        transportationService.getTransportType().getName()
                ),
                transportationService.getCompany()
        );
    }
}
