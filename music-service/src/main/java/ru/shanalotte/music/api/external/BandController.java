package ru.shanalotte.music.api.external;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.music.dto.BandDto;
import ru.shanalotte.music.dto.TrackDto;
import ru.shanalotte.music.mapper.BandDtoMapper;
import ru.shanalotte.music.mapper.TrackDtoMapper;
import ru.shanalotte.music.persistence.repository.MusicBandRepository;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

@RestController
@RequiredArgsConstructor
public class BandController {

  private final MusicBandRepository musicBandRepository;
  private final BandDtoMapper bandDtoMapper;

  @GetMapping("/band")
  public List<BandDto> allBands() {
    return musicBandRepository.findAll()
        .stream().map(bandDtoMapper::toDto)
        .collect(Collectors.toList());
  }

}
