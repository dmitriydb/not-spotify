package ru.shanalotte.music.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicGenre;
import ru.shanalotte.music.domain.MusicTrack;
import ru.shanalotte.music.dto.TrackDto;

@Service
public class TrackDtoMapper implements DtoMapper<TrackDto, MusicTrack> {
  @Override
  public TrackDto toDto(MusicTrack track) {
    TrackDto dto = new TrackDto(track.getName(), track.getLength(), mapGenres(track.getGenres()), track.getAlbumName(), track.getBandName());
    dto.setId(track.getId());
    dto.setMp3File(track.getFilePath());
    dto.setAlbumCover(track.getAlbumCover());
    return dto;
  }

  private Set<String> mapGenres(Set<MusicGenre> musicGenres) {
    return musicGenres.stream()
        .map(MusicGenre::getName)
        .collect(Collectors.toSet());
  }
}
