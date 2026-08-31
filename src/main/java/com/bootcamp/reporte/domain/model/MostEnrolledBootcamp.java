package com.bootcamp.reporte.domain.model;

import java.time.LocalDate;
import java.util.List;

public final class MostEnrolledBootcamp {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate launchDate;
    private final int durationInDays;
    private final List<ReportPerson> people;
    private final List<ReportCapability> capabilities;

    public MostEnrolledBootcamp(Long id, String name, String description, LocalDate launchDate,
                                int durationInDays, List<ReportPerson> people,
                                List<ReportCapability> capabilities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.launchDate = launchDate;
        this.durationInDays = durationInDays;
        this.people = people == null ? List.of() : List.copyOf(people);
        this.capabilities = capabilities == null ? List.of() : List.copyOf(capabilities);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public List<ReportPerson> getPeople() {
        return people;
    }

    public List<ReportCapability> getCapabilities() {
        return capabilities;
    }
}
