package com.bootcamp.reporte.domain.model;

import java.util.List;

public final class ReportCapability {

    private final Long id;
    private final String name;
    private final List<ReportTechnology> technologies;

    public ReportCapability(Long id, String name, List<ReportTechnology> technologies) {
        this.id = id;
        this.name = name;
        this.technologies = technologies == null ? List.of() : List.copyOf(technologies);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ReportTechnology> getTechnologies() {
        return technologies;
    }
}
