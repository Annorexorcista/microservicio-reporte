package com.bootcamp.reporte.domain.usecase;

import com.bootcamp.reporte.domain.api.ReportServicePort;
import com.bootcamp.reporte.domain.exception.DomainErrorCode;
import com.bootcamp.reporte.domain.exception.InvalidReportEventException;
import com.bootcamp.reporte.domain.model.MostEnrolledBootcamp;
import com.bootcamp.reporte.domain.model.ReportEvent;
import com.bootcamp.reporte.domain.spi.CapabilityGatewayPort;
import com.bootcamp.reporte.domain.spi.ReportPersistencePort;
import reactor.core.publisher.Mono;

import java.util.List;

public class ReportUseCase implements ReportServicePort {

    private static final String BOOTCAMP_CREATED = "BOOTCAMP_CREATED";
    private static final String PERSON_ENROLLED = "PERSON_ENROLLED";

    private final ReportPersistencePort persistence;
    private final CapabilityGatewayPort capabilityGateway;

    public ReportUseCase(ReportPersistencePort persistence, CapabilityGatewayPort capabilityGateway) {
        this.persistence = persistence;
        this.capabilityGateway = capabilityGateway;
    }

    @Override
    public Mono<Void> processEvent(ReportEvent event) {
        return validate(event)
                .flatMap(this::enrichBootcampEvent)
                .flatMap(persistence::processEvent);
    }

    @Override
    public Mono<MostEnrolledBootcamp> findMostEnrolled() {
        return persistence.findMostEnrolledBootcamp()
                .flatMap(bootcamp -> persistence.findPeopleByBootcamp(bootcamp.getId())
                        .collectList()
                        .map(people -> new MostEnrolledBootcamp(
                                bootcamp.getId(),
                                bootcamp.getName(),
                                bootcamp.getDescription(),
                                bootcamp.getLaunchDate(),
                                bootcamp.getDurationInDays(),
                                people,
                                bootcamp.getCapabilities())));
    }

    private Mono<ReportEvent> enrichBootcampEvent(ReportEvent event) {
        if (!BOOTCAMP_CREATED.equals(event.getEventType())
                || event.getCapabilityIds().isEmpty()
                || !event.getCapabilities().isEmpty()) {
            return Mono.just(event);
        }

        return capabilityGateway.findByIds(event.getCapabilityIds())
                .collectList()
                .map(event::withCapabilities);
    }

    private Mono<ReportEvent> validate(ReportEvent event) {
        return Mono.defer(() -> {
            if (event == null) {
                return Mono.error(new InvalidReportEventException(DomainErrorCode.EVENT_REQUIRED));
            }
            if (event.getEventId() == null || event.getEventId().isBlank()) {
                return Mono.error(new InvalidReportEventException(DomainErrorCode.EVENT_ID_REQUIRED));
            }
            if (!List.of(BOOTCAMP_CREATED, PERSON_ENROLLED).contains(event.getEventType())) {
                return Mono.error(new InvalidReportEventException(DomainErrorCode.EVENT_TYPE_INVALID));
            }
            if (event.getBootcampId() == null || event.getBootcampId() <= 0) {
                return Mono.error(new InvalidReportEventException(DomainErrorCode.EVENT_DATA_INVALID));
            }
            if (BOOTCAMP_CREATED.equals(event.getEventType())
                    && (event.getName() == null || event.getName().isBlank()
                    || event.getLaunchDate() == null
                    || event.getDurationInDays() == null
                    || event.getDurationInDays() <= 0)) {
                return Mono.error(new InvalidReportEventException(DomainErrorCode.EVENT_DATA_INVALID));
            }
            if (PERSON_ENROLLED.equals(event.getEventType())
                    && (event.getPersonName() == null || event.getPersonName().isBlank()
                    || event.getPersonEmail() == null || event.getPersonEmail().isBlank())) {
                return Mono.error(new InvalidReportEventException(DomainErrorCode.EVENT_DATA_INVALID));
            }
            return Mono.just(event);
        });
    }
}
