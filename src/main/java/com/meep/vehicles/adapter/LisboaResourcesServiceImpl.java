package com.meep.vehicles.adapter;

import com.meep.vehicles.domain.interfaces.LisboaResourcesService;
import com.meep.vehicles.domain.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class LisboaResourcesServiceImpl implements LisboaResourcesService {

    @Value("${adapter.host}")
    String baseHost;
    @Value("${adapter.vehicles.lisboa}")
    String lisboaPath;
    @Value("${adapter.vehicles.params.lowerLeftLatLon}")
    String lowerLeftLatLon;
    @Value("${adapter.vehicles.params.upperRightLatLon}")
    String upperRightLatLon;
    @Value("${adapter.vehicles.params.companyZoneIds}")
    String companyZoneIds;
    WebClient webClient;

    public LisboaResourcesServiceImpl() {
        this.webClient = WebClient.create(baseHost);
    }

    @Override
    public Flux<Vehicle> getVehicles() {
        webClient = WebClient.builder()
                .baseUrl(baseHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 Firefox/26.0")
                .build();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(lisboaPath)
                        .queryParam("lowerLeftLatLon", lowerLeftLatLon)
                        .queryParam("upperRightLatLon", upperRightLatLon)
                        .queryParam("companyZoneIds", companyZoneIds)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Vehicle.class)
                .doOnError(error -> log.error("Error in LisboaResourcesServiceImpl.getVehicles() : {}", error.getMessage()));
    }
}
