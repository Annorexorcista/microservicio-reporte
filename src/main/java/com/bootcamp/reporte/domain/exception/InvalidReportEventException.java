package com.bootcamp.reporte.domain.exception;

public class InvalidReportEventException extends RuntimeException { private final DomainErrorCode code; public InvalidReportEventException(DomainErrorCode code){super(code.getMessage());this.code=code;} public DomainErrorCode getCode(){return code;} }
