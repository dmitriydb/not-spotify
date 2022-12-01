package ru.shanalotte.scanner;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.shanalotte.music.dto.CreatedTrackDto;
import ru.shanalotte.music.dto.TrackDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicServiceProxy {

  @NonNull
  private final EurekaClient eurekaClient;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public void notifyFailedSend(TrackDto dto) {
    log.debug("Failed to send {}", dto);
  }

  @SneakyThrows
  @HystrixCommand(fallbackMethod = "notifyFailedSend")
  public void createTrack(TrackDto dto) {
    URI uri = getUri();
    String payload = objectMapper.writeValueAsString(dto);
    HttpClient httpClient = HttpClient.newBuilder().build();
    HttpRequest request = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(payload))
        .uri(uri)
        .header("Content-Type", "application/json")
        .build();
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    CreatedTrackDto responseDto = objectMapper.readValue(response.body(), CreatedTrackDto.class);
    System.out.println(responseDto);
  }

  private URI getUri() {
    InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("MUSIC-SERVICE", false);
    String host = instanceInfo.getHostName();
    int port = instanceInfo.getPort();
    return UriComponentsBuilder.newInstance()
        .host(host)
        .scheme("http")
        .port(port)
        .path("/track")
        .build()
        .toUri();
  }
}
