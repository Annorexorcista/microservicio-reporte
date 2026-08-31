package com.bootcamp.reporte.infrastructure.adapters.driving.webflux.handler;

import com.bootcamp.reporte.domain.api.ReportServicePort;
import com.bootcamp.reporte.domain.exception.DomainErrorCode;
import com.bootcamp.reporte.domain.exception.InvalidReportEventException;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto.MostEnrolledResponse;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto.ReportEventRequest;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.mapper.ReportDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ReportHandler {

    private final ReportServicePort service;
    private final ReportDtoMapper mapper;

    public ReportHandler(ReportServicePort service, ReportDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public Mono<ServerResponse> receiveEvent(ServerRequest request) {
        return request.bodyToMono(ReportEventRequest.class)
                .switchIfEmpty(Mono.error(new InvalidReportEventException(
                        DomainErrorCode.EVENT_REQUIRED)))
                .map(mapper::toDomain)
                .flatMap(service::processEvent)
                .then(ServerResponse.status(HttpStatus.ACCEPTED).build());
    }

    public Mono<ServerResponse> mostEnrolled(ServerRequest request) {
        return service.findMostEnrolled()
                .map(MostEnrolledResponse::from)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
