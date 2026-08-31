package com.bootcamp.reporte.domain.model;

public final class ReportTechnology {

    private final Long id;
    private final String name;

    public ReportTechnology(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
