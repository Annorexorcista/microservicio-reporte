package com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.mapper;

import com.bootcamp.reporte.domain.model.ReportBootcamp;
import com.bootcamp.reporte.domain.model.ReportCapability;
import com.bootcamp.reporte.domain.model.ReportEvent;
import com.bootcamp.reporte.domain.model.ReportPerson;
import com.bootcamp.reporte.domain.model.ReportTechnology;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity.ReportBootcampEntity;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity.ReportPersonEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ReportEntityMapper {

    private final ObjectMapper objectMapper;

    public ReportEntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ReportBootcampEntity toEntity(ReportEvent event, long enrollmentCount) {
        return new ReportBootcampEntity(
                event.getBootcampId(),
                event.getName(),
                event.getDescription(),
                event.getLaunchDate(),
                event.getDurationInDays(),
                event.getCapabilities().size(),
                event.getCapabilities().stream()
                        .mapToInt(capability -> capability.getTechnologies().size())
                        .sum(),
                writeCapabilities(event),
                enrollmentCount);
    }

    public ReportPersonEntity toEntity(ReportEvent event) {
        return new ReportPersonEntity(
                null,
                event.getBootcampId(),
                event.getPersonName(),
                event.getPersonEmail());
    }

    public ReportBootcamp toDomain(ReportBootcampEntity entity) {
        return new ReportBootcamp(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getLaunchDate(),
                entity.getDurationDays() == null ? 0 : entity.getDurationDays(),
                readCapabilities(entity.getCapabilitiesJson()),
                entity.getEnrollmentCount() == null ? 0 : entity.getEnrollmentCount());
    }

    public ReportPerson toDomain(ReportPersonEntity entity) {
        return new ReportPerson(entity.getBootcampId(), entity.getName(), entity.getEmail());
    }

    private String writeCapabilities(ReportEvent event) {
        try {
            return objectMapper.writeValueAsString(event.getCapabilities());
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("No fue posible serializar las capacidades", exception);
        }
    }

    private List<ReportCapability> readCapabilities(String json) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            JsonNode root = objectMapper.readTree(json);
            List<ReportCapability> capabilities = new ArrayList<>();
            for (JsonNode capability : root) {
                List<ReportTechnology> technologies = new ArrayList<>();
                for (JsonNode technology : capability.path("technologies")) {
                    technologies.add(new ReportTechnology(
                            technology.path("id").isNull() ? null : technology.path("id").asLong(),
                            technology.path("name").asText("")));
                }
                capabilities.add(new ReportCapability(
                        capability.path("id").isNull() ? null : capability.path("id").asLong(),
                        capability.path("name").asText(""),
                        technologies));
            }
            return List.copyOf(capabilities);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("No fue posible leer las capacidades del reporte", exception);
        }
    }
}
