package com.bootcamp.reporte.domain.model;

public final class ReportPerson {

    private final Long bootcampId;
    private final String name;
    private final String email;

    public ReportPerson(Long bootcampId, String name, String email) {
        this.bootcampId = bootcampId;
        this.name = name;
        this.email = email;
    }

    public Long getBootcampId() {
        return bootcampId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
