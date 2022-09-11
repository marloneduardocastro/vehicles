package com.meep.vehicles.sockets;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.meep.vehicles.domain.usecase.VehiclesUseCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VehiclesSocket {

    private final SocketIONamespace namespace;
    private final VehiclesUseCase vehiclesUseCase;

    @Autowired
    public VehiclesSocket(SocketIOServer server, VehiclesUseCase vehiclesUseCase) {
        this.vehiclesUseCase = vehiclesUseCase;
        this.namespace = server.addNamespace("/vehicles");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
    }

    private ConnectListener onConnected() {
        return client -> log.info("Client[{}] - Connected", client.getSessionId().toString());
    }

    private DisconnectListener onDisconnected() {
        return client -> log.info("Client[{}] - Disconnected", client.getSessionId().toString());
    }

    @Scheduled(cron = "${sockets.cron.getVehicles}")
    private void consultChangesInLisboaVehicles(){
        log.info("Iniciando captura de vehiculos...");
        vehiclesUseCase.getChangesLisboaVehicles()
                .filter(changesVehiclesLisboa -> Strings.isNotBlank(changesVehiclesLisboa.getErrorMessage())
                        || !(changesVehiclesLisboa.getTotalDeletedVehicles().equals(0) && changesVehiclesLisboa.getTotalAddedVehiclesAdded().equals(0))
                )
                .doOnNext(changeVehiclesDTO -> namespace.getBroadcastOperations().sendEvent("lisboaChanges", changeVehiclesDTO))
                .blockOptional();
    }
}
