package ru.shanalotte.music.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.domain.MusicGenre;
import ru.shanalotte.music.domain.MusicTrack;
import ru.shanalotte.music.dto.BandDto;
import ru.shanalotte.music.dto.TrackDto;

@Service
public class BandDtoMapper implements DtoMapper<BandDto, MusicBand> {
  @Override
  public BandDto toDto(MusicBand band) {
    return new BandDto(band.getId(), band.getName());
  }
}
