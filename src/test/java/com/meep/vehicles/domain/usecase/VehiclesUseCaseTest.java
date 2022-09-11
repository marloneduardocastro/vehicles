package com.meep.vehicles.domain.usecase;

import com.meep.vehicles.domain.dto.ChangeVehiclesDTO;
import com.meep.vehicles.domain.interfaces.LisboaResourcesService;
import com.meep.vehicles.domain.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

class VehiclesUseCaseTest {

    @InjectMocks
    VehiclesUseCase vehiclesUseCase;

    @Mock
    LisboaResourcesService lisboaResourcesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        VehiclesUseCase.currentVehiclesMap.clear();
    }

    @Test
    void NotChangesInVehiclesLisboa(){
        Mockito.when(lisboaResourcesService.getVehicles()).thenReturn(Flux.just(Vehicle.builder().id(UUID.randomUUID().toString()).build()));
        Mono<ChangeVehiclesDTO> execute = vehiclesUseCase.getChangesLisboaVehicles();
        StepVerifier.create(execute)
                .expectNext(ChangeVehiclesDTO.builder()
                        .totalAddedVehiclesAdded(0)
                        .totalDeletedVehicles(0)
                        .build()
                )
                .verifyComplete();
    }


    @Test
    void HasChangesVehiclesLisboa(){
        Vehicle currentVehicle = Vehicle.builder().id(UUID.randomUUID().toString()).build();
        Vehicle newVehicle = Vehicle.builder().id(UUID.randomUUID().toString()).build();
        Mockito.when(lisboaResourcesService.getVehicles()).thenReturn(Flux.just(newVehicle));
        VehiclesUseCase.currentVehiclesMap.put(currentVehicle.getId(), currentVehicle);
        Mono<ChangeVehiclesDTO> execute = vehiclesUseCase.getChangesLisboaVehicles();
        StepVerifier.create(execute)
                .expectNext(ChangeVehiclesDTO.builder()
                        .addedVehiclesAdded(List.of(newVehicle))
                        .deletedVehicles(List.of(currentVehicle))
                        .totalAddedVehiclesAdded(1)
                        .totalDeletedVehicles(1)
                        .build()
                )
                .verifyComplete();
    }
}