package com.bootcamp.reporte.domain.spi;

import com.bootcamp.reporte.domain.model.ReportBootcamp;
import com.bootcamp.reporte.domain.model.ReportEvent;
import com.bootcamp.reporte.domain.model.ReportPerson;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportPersistencePort {

    Mono<Void> processEvent(ReportEvent event);

    Mono<ReportBootcamp> findMostEnrolledBootcamp();

    Flux<ReportPerson> findPeopleByBootcamp(Long bootcampId);
}
