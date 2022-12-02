package ru.shanalotte.spotify.gateway.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.shanalotte.music.dto.TrackDto;
import ru.shanalotte.spotify.gateway.dto.GatewayResponseDto;
import ru.shanalotte.spotify.gateway.dto.GatewayResponseStatus;

@RestController
@RequiredArgsConstructor
public class TrackController {

  private final EurekaClient eurekaClient;
  private final ObjectMapper objectMapper;

  public ResponseEntity<GatewayResponseDto> inCaseOfFail() {
    return ResponseEntity
        .ok(new GatewayResponseDto(GatewayResponseStatus.ERROR,
            new ArrayList<>()));
  }

  @SneakyThrows
  @GetMapping("/track")
  @CrossOrigin("http://localhost:3000")
  @HystrixCommand(fallbackMethod = "inCaseOfFail")
  public ResponseEntity<GatewayResponseDto> getAllTracks() {
    InstanceInfo musicService = eurekaClient.getNextServerFromEureka("MUSIC-SERVICE", false);
    URI uri = buildURI(musicService);
    HttpClient httpClient = HttpClient.newBuilder().build();
    HttpRequest request = buildRequest(uri);
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    TrackDto[] trackDtos = objectMapper.readValue(response.body(), TrackDto[].class);
    return ResponseEntity
        .ok(new GatewayResponseDto(GatewayResponseStatus.OK,
            Arrays.stream(trackDtos).collect(Collectors.toList())));
  }

  private HttpRequest buildRequest(URI uri) {
    return HttpRequest.newBuilder()
        .GET()
        .uri(uri)
        .build();
  }

  private URI buildURI(InstanceInfo musicService) {
    return UriComponentsBuilder.newInstance()
        .host(musicService.getHostName())
        .port(musicService.getPort())
        .path("/track")
        .scheme("http")
        .build()
        .toUri();
  }

}
