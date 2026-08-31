package com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository;

import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity.ReportBootcampEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ReportBootcampRepository extends ReactiveCrudRepository<ReportBootcampEntity, Long> {
    Mono<ReportBootcampEntity> findFirstByOrderByEnrollmentCountDescIdAsc();
}
