package com.meep.vehicles.domain.usecase;

import com.meep.vehicles.domain.dto.ChangeVehiclesDTO;
import com.meep.vehicles.domain.interfaces.LisboaResourcesService;
import com.meep.vehicles.domain.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class VehiclesUseCase {

    protected static final ConcurrentMap<String, Vehicle> currentVehiclesMap = new ConcurrentHashMap<>();

    private final LisboaResourcesService lisboaResourcesService;

    @Autowired
    public VehiclesUseCase(LisboaResourcesService lisboaResourcesService) {
        this.lisboaResourcesService = lisboaResourcesService;
    }

    public Mono<ChangeVehiclesDTO> getChangesLisboaVehicles(){
        ConcurrentMap<String, Vehicle> newVehiclesMap = new ConcurrentHashMap<>();

        return lisboaResourcesService.getVehicles()
                .doOnNext(vehicle -> newVehiclesMap.put(vehicle.getId(), vehicle))
                .collectList()
                .filter(vehicles -> !currentVehiclesMap.isEmpty())
                .map(vehicles -> ChangeVehiclesDTO.builder()
                        .addedVehiclesAdded(getVehicleNotMatches(newVehiclesMap, currentVehiclesMap))
                        .deletedVehicles(getVehicleNotMatches(currentVehiclesMap, newVehiclesMap))
                        .build()
                )
                .map(changeVehiclesDTO -> changeVehiclesDTO.toBuilder()
                        .totalDeletedVehicles(changeVehiclesDTO.getDeletedVehicles().size())
                        .totalAddedVehiclesAdded(changeVehiclesDTO.getAddedVehiclesAdded().size())
                        .build()
                )
                .defaultIfEmpty(ChangeVehiclesDTO.builder().totalAddedVehiclesAdded(0).totalDeletedVehicles(0).build())
                .doOnNext(changeVehiclesDTO -> currentVehiclesMap.clear())
                .doOnNext(changeVehiclesDTO -> currentVehiclesMap.putAll(newVehiclesMap))
                .onErrorResume(throwable -> Mono.just(ChangeVehiclesDTO.builder().errorMessage(throwable.getMessage()).build()));
    }

    private List<Vehicle> getVehicleNotMatches(ConcurrentMap<String, Vehicle> concurrentMapToFind, ConcurrentMap<String, Vehicle> concurrentMapToMatches) {
        return concurrentMapToFind
                .values().stream()
                .filter(vehicleFind -> concurrentMapToMatches.values().stream()
                        .noneMatch(vehicleMatch -> vehicleMatch.getId().equals(vehicleFind.getId()))
                )
                .collect(Collectors.toList());
    }
}
