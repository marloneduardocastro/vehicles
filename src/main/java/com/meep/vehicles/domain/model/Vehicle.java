package com.meep.vehicles.domain.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Vehicle implements Serializable {
    private String id;
    private String name;
    private double x;
    private double y;
    private String licencePlate;
    private int range;
    private int batteryLevel;
    private int helmets;
    private String model;
    private String resourceImageId;
    private ArrayList<String> resourcesImagesUrls;
    private boolean realTimeData;
    private String resourceType;
    private int companyZoneId;
}
