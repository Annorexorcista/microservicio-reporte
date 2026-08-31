package com.bootcamp.reporte.domain.model;

import java.time.LocalDate;
import java.util.List;

public final class ReportEvent {

    private final String eventId;
    private final String eventType;
    private final Long bootcampId;
    private final String name;
    private final String description;
    private final LocalDate launchDate;
    private final Integer durationInDays;
    private final List<Long> capabilityIds;
    private final List<ReportCapability> capabilities;
    private final String personName;
    private final String personEmail;

    public ReportEvent(String eventId, String eventType, Long bootcampId, String name,
                       String description, LocalDate launchDate, Integer durationInDays,
                       List<Long> capabilityIds, String personName, String personEmail) {
        this(eventId, eventType, bootcampId, name, description, launchDate, durationInDays,
                capabilityIds, List.of(), personName, personEmail);
    }

    public ReportEvent(String eventId, String eventType, Long bootcampId, String name,
                       String description, LocalDate launchDate, Integer durationInDays,
                       List<Long> capabilityIds, List<ReportCapability> capabilities,
                       String personName, String personEmail) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.bootcampId = bootcampId;
        this.name = name;
        this.description = description;
        this.launchDate = launchDate;
        this.durationInDays = durationInDays;
        this.capabilityIds = capabilityIds == null ? List.of() : List.copyOf(capabilityIds);
        this.capabilities = capabilities == null ? List.of() : List.copyOf(capabilities);
        this.personName = personName;
        this.personEmail = personEmail;
    }

    public ReportEvent withCapabilities(List<ReportCapability> enrichedCapabilities) {
        return new ReportEvent(eventId, eventType, bootcampId, name, description, launchDate,
                durationInDays, capabilityIds, enrichedCapabilities, personName, personEmail);
    }

    public String getEventId() { return eventId; }
    public String getEventType() { return eventType; }
    public Long getBootcampId() { return bootcampId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getLaunchDate() { return launchDate; }
    public Integer getDurationInDays() { return durationInDays; }
    public List<Long> getCapabilityIds() { return capabilityIds; }
    public List<ReportCapability> getCapabilities() { return capabilities; }
    public String getPersonName() { return personName; }
    public String getPersonEmail() { return personEmail; }
}
