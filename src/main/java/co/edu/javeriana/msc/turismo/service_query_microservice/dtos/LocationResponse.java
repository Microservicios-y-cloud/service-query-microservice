package co.edu.javeriana.msc.turismo.service_publication_microservice.dto;

public record LocationResponse (
    Long id,
    String address,
    Double latitude,
    Double longitude,
    String country,
    String city,
    String municipality
) {}
