package com.bootcamp.reporte.infrastructure.adapters.driving.webflux.dto;
import java.time.Instant;
public record ErrorResponse(int status,String code,String message,Instant timestamp) { }
