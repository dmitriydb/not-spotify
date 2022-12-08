package ru.shanalotte.spotify.gateway.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.validation.Valid;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.shanalotte.user.dto.RegistrationDto;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

  private final EurekaClient eurekaClient;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @SneakyThrows
  @CrossOrigin({"http://localhost:3000", "http://10.1.7.155:3000"})
  @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
  public @ResponseBody ResponseEntity<?> register(@Valid @RequestBody RegistrationDto registrationDto) {
    InstanceInfo userService = eurekaClient.getNextServerFromEureka("USER-SERVICE", false);
    URI uri = buildURI(userService);
    HttpClient httpClient = HttpClient.newBuilder().build();
    HttpRequest request = buildRequest(uri, objectMapper.writeValueAsString(registrationDto));
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    log.info("{} {}", response.statusCode(), response.body());
    return ResponseEntity.status(response.statusCode())
        .body(response.body());
  }

  private HttpRequest buildRequest(URI uri, String payload) {
    return HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(payload))
        .uri(uri)
        .header("Content-Type", "application/json")
        .build();
  }


  private URI buildURI(InstanceInfo musicService) {
    return UriComponentsBuilder.newInstance()
        .host(musicService.getHostName())
        .port(musicService.getPort())
        .path("/register")
        .scheme("http")
        .build()
        .toUri();
  }

}
