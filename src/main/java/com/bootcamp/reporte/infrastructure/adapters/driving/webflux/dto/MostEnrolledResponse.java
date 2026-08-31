package com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto;

import com.bootcamp.reporte.domain.model.MostEnrolledBootcamp;
import java.time.LocalDate;
import java.util.List;

public record MostEnrolledResponse(
        Long id,
        String name,
        String description,
        LocalDate launchDate,
        int durationInDays,
        List<PersonResponse> people,
        List<CapabilityResponse> capabilities) {

    public static MostEnrolledResponse from(MostEnrolledBootcamp bootcamp) {
        List<PersonResponse> people = bootcamp.getPeople().stream()
                .map(person -> new PersonResponse(person.getName(), person.getEmail()))
                .toList();

        List<CapabilityResponse> capabilities = bootcamp.getCapabilities().stream()
                .map(capability -> new CapabilityResponse(
                        capability.getId(),
                        capability.getName(),
                        capability.getTechnologies().stream()
                                .map(technology -> new TechnologyResponse(
                                        technology.getId(), technology.getName()))
                                .toList()))
                .toList();

        return new MostEnrolledResponse(
                bootcamp.getId(),
                bootcamp.getName(),
                bootcamp.getDescription(),
                bootcamp.getLaunchDate(),
                bootcamp.getDurationInDays(),
                people,
                capabilities);
    }

    public record PersonResponse(String name, String email) {
    }

    public record CapabilityResponse(
            Long id,
            String name,
            List<TechnologyResponse> technologies) {
    }

    public record TechnologyResponse(Long id, String name) {
    }
}
