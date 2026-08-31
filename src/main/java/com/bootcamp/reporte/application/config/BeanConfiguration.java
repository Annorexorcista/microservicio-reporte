package com.bootcamp.reporte.application.config;

import com.bootcamp.reporte.domain.api.ReportServicePort;
import com.bootcamp.reporte.domain.spi.CapabilityGatewayPort;
import com.bootcamp.reporte.domain.spi.ReportPersistencePort;
import com.bootcamp.reporte.domain.usecase.ReportUseCase;
import com.bootcamp.reporte.infrastructure.adapters.driven.http.CapabilityGatewayAdapter;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.adapter.ReportPersistenceAdapter;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.mapper.ReportEntityMapper;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository.ReportBootcampRepository;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository.ReportEventRepository;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository.ReportPersonRepository;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.handler.ReportHandler;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.mapper.ReportDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    public ReportEntityMapper reportEntityMapper(ObjectMapper objectMapper) {
        return new ReportEntityMapper(objectMapper);
    }

    @Bean
    public ReportDtoMapper reportDtoMapper() {
        return new ReportDtoMapper();
    }

    @Bean
    public ReportPersistencePort reportPersistencePort(
            ReportEventRepository events,
            ReportBootcampRepository bootcamps,
            ReportPersonRepository people,
            ReportEntityMapper mapper,
            TransactionalOperator transaction,
            R2dbcEntityTemplate entityTemplate) {
        return new ReportPersistenceAdapter(
                events, bootcamps, people, mapper, transaction, entityTemplate);
    }

    @Bean
    public CapabilityGatewayPort capabilityGatewayPort(WebClient capabilityWebClient) {
        return new CapabilityGatewayAdapter(capabilityWebClient);
    }

    @Bean
    public ReportServicePort reportServicePort(
            ReportPersistencePort persistence,
            CapabilityGatewayPort capabilityGateway) {
        return new ReportUseCase(persistence, capabilityGateway);
    }

    @Bean
    public ReportHandler reportHandler(ReportServicePort service, ReportDtoMapper mapper) {
        return new ReportHandler(service, mapper);
    }
}
