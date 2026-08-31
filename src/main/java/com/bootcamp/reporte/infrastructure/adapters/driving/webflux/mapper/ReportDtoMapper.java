package com.bootcamp.reporte.infrastructure.adapters.driving.webflux.mapper;

import com.bootcamp.reporte.domain.model.ReportEvent;
import com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto.ReportEventRequest;

public class ReportDtoMapper {

    public ReportEvent toDomain(ReportEventRequest request) {
        return new ReportEvent(
                request.eventId(),
                request.eventType(),
                request.bootcampId(),
                request.name(),
                request.description(),
                request.launchDate(),
                request.durationInDays(),
                request.capabilityIds(),
                request.personName(),
                request.personEmail());
    }
}
