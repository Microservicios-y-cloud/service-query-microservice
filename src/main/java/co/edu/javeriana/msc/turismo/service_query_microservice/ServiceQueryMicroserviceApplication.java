package co.edu.javeriana.msc.turismo.service_query_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceQueryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceQueryMicroserviceApplication.class, args);
	}

}
