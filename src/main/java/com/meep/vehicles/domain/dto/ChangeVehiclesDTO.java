package com.meep.vehicles.domain.dto;

import com.meep.vehicles.domain.model.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ChangeVehiclesDTO {

    private Integer totalDeletedVehicles;
    private Integer totalAddedVehiclesAdded;
    private List<Vehicle> deletedVehicles;
    private List<Vehicle> addedVehiclesAdded;
    private String errorMessage;
}
