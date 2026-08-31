package com.bootcamp.reporte.domain.spi;

import com.bootcamp.reporte.domain.model.ReportCapability;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface CapabilityGatewayPort {
    Flux<ReportCapability> findByIds(Collection<Long> ids);
}
