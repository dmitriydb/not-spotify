package ru.shanalotte.spotify.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@EnableCircuitBreaker
@ComponentScan("ru.shanalotte.spotify.gateway")
public class GatewayApiLauncher {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApiLauncher.class, args);
  }

}
