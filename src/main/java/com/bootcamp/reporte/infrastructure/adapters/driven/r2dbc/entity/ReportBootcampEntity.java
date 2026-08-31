package com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("report_bootcamp")
public class ReportBootcampEntity {

    @Id
    private Long id;
    private String name;
    private String description;

    @Column("launch_date")
    private LocalDate launchDate;

    @Column("duration_days")
    private Integer durationDays;

    @Column("capability_count")
    private Integer capabilityCount;

    @Column("technology_count")
    private Integer technologyCount;

    @Column("capabilities_json")
    private String capabilitiesJson;

    @Column("enrollment_count")
    private Long enrollmentCount;

    public ReportBootcampEntity() {
    }

    public ReportBootcampEntity(Long id, String name, String description, LocalDate launchDate,
                                Integer durationDays, Integer capabilityCount,
                                Integer technologyCount, String capabilitiesJson,
                                Long enrollmentCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.launchDate = launchDate;
        this.durationDays = durationDays;
        this.capabilityCount = capabilityCount;
        this.technologyCount = technologyCount;
        this.capabilitiesJson = capabilitiesJson;
        this.enrollmentCount = enrollmentCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getLaunchDate() { return launchDate; }
    public void setLaunchDate(LocalDate launchDate) { this.launchDate = launchDate; }
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    public Integer getCapabilityCount() { return capabilityCount; }
    public void setCapabilityCount(Integer capabilityCount) { this.capabilityCount = capabilityCount; }
    public Integer getTechnologyCount() { return technologyCount; }
    public void setTechnologyCount(Integer technologyCount) { this.technologyCount = technologyCount; }
    public String getCapabilitiesJson() { return capabilitiesJson; }
    public void setCapabilitiesJson(String capabilitiesJson) { this.capabilitiesJson = capabilitiesJson; }
    public Long getEnrollmentCount() { return enrollmentCount; }
    public void setEnrollmentCount(Long enrollmentCount) { this.enrollmentCount = enrollmentCount; }
}
