package com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.repository;
import com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity.ReportEventEntity; import org.springframework.data.repository.reactive.ReactiveCrudRepository;
public interface ReportEventRepository extends ReactiveCrudRepository<ReportEventEntity,String> { }
