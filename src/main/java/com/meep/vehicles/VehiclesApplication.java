package com.meep.vehicles;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class VehiclesApplication {

	@Value("${sockets.port}")
	private Integer serverPort;

	@Value("${sockets.host}")
	private String serverHost;

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setHostname(serverHost);
		config.setPort(serverPort);
		return new SocketIOServer(config);
	}

	public static void main(String[] args) {
		SpringApplication.run(VehiclesApplication.class, args);
		String hostAddress = getHostAddress();

		log.info("-----------------------------------------------");
		log.info("Application is running!");
		log.info("host: {}", hostAddress);
		log.info("-----------------------------------------------");
	}

	private static String getHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.warn("The host name could not be determined, using 'localhost' as fallback");
			return "localhost";
		}
	}

}
