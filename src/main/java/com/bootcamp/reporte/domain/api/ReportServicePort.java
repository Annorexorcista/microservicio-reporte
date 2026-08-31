package com.bootcamp.reporte.domain.api;

import com.bootcamp.reporte.domain.model.MostEnrolledBootcamp;
import com.bootcamp.reporte.domain.model.ReportEvent;
import reactor.core.publisher.Mono;

public interface ReportServicePort {

    Mono<Void> processEvent(ReportEvent event);

    Mono<MostEnrolledBootcamp> findMostEnrolled();
}
