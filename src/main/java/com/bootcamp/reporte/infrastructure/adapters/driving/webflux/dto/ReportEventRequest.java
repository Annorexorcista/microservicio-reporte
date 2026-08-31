package com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto;

import java.time.LocalDate;
import java.util.List;

public record ReportEventRequest(
        String eventId,
        String eventType,
        Long bootcampId,
        String name,
        String description,
        LocalDate launchDate,
        Integer durationInDays,
        List<Long> capabilityIds,
        String personName,
        String personEmail) {
}
