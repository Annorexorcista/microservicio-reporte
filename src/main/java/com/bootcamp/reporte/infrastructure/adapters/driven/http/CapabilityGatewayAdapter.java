package com.bootcamp.reporte.infrastructure.adapters.driven.http;

import com.bootcamp.reporte.domain.exception.ReportGatewayException;
import com.bootcamp.reporte.domain.model.ReportCapability;
import com.bootcamp.reporte.domain.model.ReportTechnology;
import com.bootcamp.reporte.domain.spi.CapabilityGatewayPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.stream.Collectors;

public class CapabilityGatewayAdapter implements CapabilityGatewayPort {

    private final WebClient client;

    public CapabilityGatewayAdapter(WebClient client) {
        this.client = client;
    }

    @Override
    public Flux<ReportCapability> findByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }

        String csv = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return client.get()
                .uri(uri -> uri.path("/api/v1/capabilities")
                        .queryParam("ids", csv)
                        .build())
                .retrieve()
                .bodyToFlux(CapabilityResponse.class)
                .map(this::toDomain)
                .onErrorMap(error -> new ReportGatewayException(error));
    }

    private ReportCapability toDomain(CapabilityResponse response) {
        return new ReportCapability(
                response.id(),
                response.name(),
                response.technologies().stream()
                        .map(technology -> new ReportTechnology(technology.id(), technology.name()))
                        .toList());
    }

    private record CapabilityResponse(
            Long id,
            String name,
            String description,
            java.util.List<TechnologyResponse> technologies) {
    }

    private record TechnologyResponse(Long id, String name) {
    }
}
