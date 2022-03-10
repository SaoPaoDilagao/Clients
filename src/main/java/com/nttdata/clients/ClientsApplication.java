package com.nttdata.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Client application.
 */
@SpringBootApplication
@EnableEurekaClient
public class ClientsApplication {
  public static void main(String[] args) {
    SpringApplication.run(ClientsApplication.class, args);
  }
}
