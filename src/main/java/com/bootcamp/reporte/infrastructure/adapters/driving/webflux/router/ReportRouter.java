package com.bootcamp.reporte.infrastructure.adapters.driving.webflux.router;

import com.bootcamp.reporte.domain.api.ReportServicePort;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto.ErrorResponse;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto.MostEnrolledResponse;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto.ReportEventRequest;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.handler.ReportHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class ReportRouter {

    private static final String EVENTS_PATH = "/api/v1/reports/events";
    private static final String MOST_ENROLLED_PATH = "/api/v1/reports/bootcamps/most-enrolled";

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = EVENTS_PATH,
                    method = RequestMethod.POST,
                    beanClass = ReportServicePort.class,
                    beanMethod = "processEvent",
                    operation = @Operation(
                            operationId = "receiveReportEvent",
                            summary = "Procesa un evento de reporte",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ReportEventRequest.class))),
                            responses = @ApiResponse(
                                    responseCode = "202",
                                    description = "Evento procesado"))),
            @RouterOperation(
                    path = MOST_ENROLLED_PATH,
                    method = RequestMethod.GET,
                    beanClass = ReportServicePort.class,
                    beanMethod = "findMostEnrolled",
                    operation = @Operation(
                            operationId = "findMostEnrolledBootcamp",
                            summary = "Obtiene el bootcamp con más personas inscritas",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Bootcamp con mayor número de inscritos",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(
                                                            implementation = MostEnrolledResponse.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno",
                                            content = @Content(
                                                    schema = @Schema(implementation = ErrorResponse.class)))
                            }))
    })
    public RouterFunction<ServerResponse> reportRoutes(ReportHandler handler) {
        return RouterFunctions.route()
                .POST(EVENTS_PATH, accept(MediaType.APPLICATION_JSON), handler::receiveEvent)
                .GET(MOST_ENROLLED_PATH, accept(MediaType.APPLICATION_JSON), handler::mostEnrolled)
                .build();
    }
}
