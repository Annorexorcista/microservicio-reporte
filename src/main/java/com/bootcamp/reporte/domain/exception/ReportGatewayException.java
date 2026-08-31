package com.bootcamp.reporte.domain.exception;

public class ReportGatewayException extends RuntimeException {

    public ReportGatewayException(Throwable cause) {
        super("No fue posible enriquecer las capacidades del bootcamp", cause);
    }
}
