package com.bootcamp.reporte.domain.exception;

public enum DomainErrorCode {
    EVENT_REQUIRED("El evento es obligatorio"), EVENT_ID_REQUIRED("eventId es obligatorio"), EVENT_TYPE_INVALID("eventType no soportado"), EVENT_DATA_INVALID("Los datos del evento son inválidos");
    private final String message; DomainErrorCode(String message){this.message=message;} public String getCode(){return name();} public String getMessage(){return message;}
}
