package ru.shanalotte.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("ru.shanalotte.user")
@EnableMongoRepositories
@EnableEurekaClient
public class UserServiceLauncher{
  public static void main(String[] args) {
    SpringApplication.run(UserServiceLauncher.class, args);
  }
}
