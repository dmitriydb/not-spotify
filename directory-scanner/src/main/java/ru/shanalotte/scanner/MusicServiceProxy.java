package ru.shanalotte.scanner;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.dto.CreatedTrackDto;
import ru.shanalotte.music.dto.TrackDto;

@Service
@RequiredArgsConstructor
public class MusicServiceProxy {

  @NonNull
  private final EurekaClient eurekaClient;
  private final ObjectMapper objectMapper = new ObjectMapper();



  @SneakyThrows
  public void createTrack(TrackDto dto) {
    String payload = objectMapper.writeValueAsString(dto);
    HttpClient httpClient = HttpClient.newBuilder().build();
    HttpRequest request = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(payload))
        .uri(URI.create("http://localhost:12345/track"))
        .header("Content-Type", "application/json")
        .build();
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    CreatedTrackDto responseDto = objectMapper.readValue(response.body(), CreatedTrackDto.class);
    System.out.println(responseDto);
  }
}
