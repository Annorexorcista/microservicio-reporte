package com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.adapter;

import com.bootcamp.reporte.domain.model.ReportBootcamp;
import com.bootcamp.reporte.domain.model.ReportEvent;
import com.bootcamp.reporte.domain.model.ReportPerson;
import com.bootcamp.reporte.domain.spi.ReportPersistencePort;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity.ReportBootcampEntity;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity.ReportEventEntity;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.mapper.ReportEntityMapper;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository.ReportBootcampRepository;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository.ReportEventRepository;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository.ReportPersonRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReportPersistenceAdapter implements ReportPersistencePort {

    private final ReportEventRepository events;
    private final ReportBootcampRepository bootcamps;
    private final ReportPersonRepository people;
    private final ReportEntityMapper mapper;
    private final TransactionalOperator transaction;
    private final R2dbcEntityTemplate entityTemplate;

    public ReportPersistenceAdapter(ReportEventRepository events,
                                    ReportBootcampRepository bootcamps,
                                    ReportPersonRepository people,
                                    ReportEntityMapper mapper,
                                    TransactionalOperator transaction,
                                    R2dbcEntityTemplate entityTemplate) {
        this.events = events;
        this.bootcamps = bootcamps;
        this.people = people;
        this.mapper = mapper;
        this.transaction = transaction;
        this.entityTemplate = entityTemplate;
    }

    @Override
    public Mono<Void> processEvent(ReportEvent event) {
        Mono<Void> pipeline = events.existsById(event.getEventId())
                .flatMap(processed -> processed
                        ? Mono.empty()
                        : events.save(new ReportEventEntity(event.getEventId()))
                                .then(apply(event)));
        return pipeline.as(transaction::transactional);
    }

    private Mono<Void> apply(ReportEvent event) {
        if ("BOOTCAMP_CREATED".equals(event.getEventType())) {
            return people.countByBootcampId(event.getBootcampId())
                    .defaultIfEmpty(0L)
                    .flatMap(count -> bootcamps.save(mapper.toEntity(event, count)))
                    .then();
        }

        return people.existsByBootcampIdAndEmail(event.getBootcampId(), event.getPersonEmail())
                .flatMap(alreadyStored -> alreadyStored
                        ? Mono.empty()
                        : people.save(mapper.toEntity(event))
                                .then(incrementEnrollmentCount(event.getBootcampId())))
                .then();
    }

    private Mono<Void> incrementEnrollmentCount(Long bootcampId) {
        return entityTemplate.getDatabaseClient()
                .sql("UPDATE report_bootcamp SET enrollment_count = enrollment_count + 1 "
                        + "WHERE id = :bootcampId")
                .bind("bootcampId", bootcampId)
                .fetch()
                .rowsUpdated()
                .then();
    }

    @Override
    public Mono<ReportBootcamp> findMostEnrolledBootcamp() {
        return bootcamps.findFirstByOrderByEnrollmentCountDescIdAsc()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ReportPerson> findPeopleByBootcamp(Long bootcampId) {
        return people.findByBootcampId(bootcampId)
                .map(mapper::toDomain);
    }
}
