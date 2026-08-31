package com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository;

import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity.ReportPersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportPersonRepository extends ReactiveCrudRepository<ReportPersonEntity, Long> {
    Flux<ReportPersonEntity> findByBootcampId(Long bootcampId);
    Mono<Boolean> existsByBootcampIdAndEmail(Long bootcampId, String email);
    Mono<Long> countByBootcampId(Long bootcampId);
}
