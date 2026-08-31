package com.bootcamp.reporte.infrastructure.adapters.driven.r2dbc.entity;
import org.springframework.data.annotation.Id; import org.springframework.data.relational.core.mapping.Table;
@Table("report_event") public class ReportEventEntity { @Id private String eventId; public ReportEventEntity(){} public ReportEventEntity(String id){eventId=id;} public String getEventId(){return eventId;} public void setEventId(String id){eventId=id;} }
