package com.meep.vehicles.domain.interfaces;

import com.meep.vehicles.domain.model.Vehicle;
import reactor.core.publisher.Flux;

public interface LisboaResourcesService {

    Flux<Vehicle> getVehicles();
}
