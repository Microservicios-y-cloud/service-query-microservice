package co.edu.javeriana.msc.turismo.service_query_microservice.controllers;

import co.edu.javeriana.msc.turismo.service_query_microservice.dto.queue.SuperService;
import co.edu.javeriana.msc.turismo.service_query_microservice.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QueryServicesController {

    private final ServiceService serviceService;
    @QueryMapping
    public List<SuperService> servicesByKeyword(@Argument String keyword) {
        //TODO corregir para que aparezca como atributo un objeto
        return serviceService.findAllByKeyword(keyword);
    }
}
