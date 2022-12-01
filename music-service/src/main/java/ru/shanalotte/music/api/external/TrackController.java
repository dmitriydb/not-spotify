package ru.shanalotte.music.api.external;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.music.dto.TrackDto;
import ru.shanalotte.music.mapper.TrackDtoMapper;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

@RestController
@RequiredArgsConstructor
public class TrackController {

  private final MusicTrackRepository musicTrackRepository;
  private final TrackDtoMapper trackDtoMapper;

  @GetMapping("/track")
  public List<TrackDto> allTracks() {
    return musicTrackRepository.findAll()
        .stream().map(trackDtoMapper::toDto)
        .collect(Collectors.toList());
  }

}
