package com.bootcamp.reporte.domain.model;

import java.time.LocalDate;
import java.util.List;

public final class ReportBootcamp {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate launchDate;
    private final int durationInDays;
    private final List<ReportCapability> capabilities;
    private final long enrollmentCount;

    public ReportBootcamp(Long id, String name, String description, LocalDate launchDate,
                          int durationInDays, List<ReportCapability> capabilities,
                          long enrollmentCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.launchDate = launchDate;
        this.durationInDays = durationInDays;
        this.capabilities = capabilities == null ? List.of() : List.copyOf(capabilities);
        this.enrollmentCount = enrollmentCount;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getLaunchDate() { return launchDate; }
    public int getDurationInDays() { return durationInDays; }
    public List<ReportCapability> getCapabilities() { return capabilities; }
    public long getEnrollmentCount() { return enrollmentCount; }
}
