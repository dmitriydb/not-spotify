package ru.shanalotte.spotify.gateway.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.shanalotte.music.dto.TrackDto;
import ru.shanalotte.spotify.gateway.dto.GatewayResponseDto;
import ru.shanalotte.spotify.gateway.dto.GatewayResponseStatus;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TrackController {

  private volatile List<TrackDto> cachedSongs;
  private final EurekaClient eurekaClient;
  private final ObjectMapper objectMapper;

  public ResponseEntity<GatewayResponseDto> inCaseOfFail() {
    return ResponseEntity
        .ok(new GatewayResponseDto(GatewayResponseStatus.ERROR,
            new ArrayList<>()));
  }

  public ResponseEntity<GatewayResponseDto> inCaseOfFail(int amount) {
    return ResponseEntity
        .ok(new GatewayResponseDto(GatewayResponseStatus.ERROR,
            new ArrayList<>()));
  }

  @SneakyThrows
  private List<TrackDto> allTracks() {
    if (cachedSongs != null) {
      log.info("RETURNING CACHED SONGS");
      return cachedSongs;
    } else {
      InstanceInfo musicService = eurekaClient.getNextServerFromEureka("MUSIC-SERVICE", false);
      URI uri = buildURI(musicService);
      HttpClient httpClient = HttpClient.newBuilder().build();
      HttpRequest request = buildRequest(uri);
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      TrackDto[] trackDtos = objectMapper.readValue(response.body(), TrackDto[].class);
      this.cachedSongs = Arrays.stream(trackDtos).collect(Collectors.toList());
      return this.cachedSongs;
    }
  }

  @SneakyThrows
  @GetMapping("/track")
  @CrossOrigin({"http://localhost:3000", "http://10.1.7.155:3000"})
  @HystrixCommand(fallbackMethod = "inCaseOfFail")
  public ResponseEntity<GatewayResponseDto> getAllTracks() {
    return ResponseEntity
        .ok(new GatewayResponseDto(GatewayResponseStatus.OK,
            allTracks()));
  }

  @SneakyThrows
  @GetMapping("/random/{amount}")
  @CrossOrigin({"http://localhost:3000", "http://10.1.7.155:3000"})
  @HystrixCommand(fallbackMethod = "inCaseOfFail")
  public ResponseEntity<GatewayResponseDto> random10Tracks(@PathVariable("amount") int amount) {
    var tracks = new ArrayList<>(allTracks());
    Collections.shuffle(tracks);
    return ResponseEntity
        .ok(new GatewayResponseDto(GatewayResponseStatus.OK,
            tracks.stream().limit(amount).collect(Collectors.toList())));
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
