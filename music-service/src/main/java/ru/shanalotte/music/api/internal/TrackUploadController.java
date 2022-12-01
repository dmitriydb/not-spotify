package ru.shanalotte.music.api.internal;

import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.domain.MusicGenre;
import ru.shanalotte.music.domain.MusicTrack;
import ru.shanalotte.music.dto.CreatedTrackDto;
import ru.shanalotte.music.dto.TrackDto;
import ru.shanalotte.music.service.album.AlbumCreationService;
import ru.shanalotte.music.service.album.AlbumUpdateService;
import ru.shanalotte.music.service.band.BandCreationService;
import ru.shanalotte.music.service.genre.GenreCreationService;
import ru.shanalotte.music.service.track.TrackCreationService;

@RestController
@RequiredArgsConstructor
public class TrackUploadController {

  private final BandCreationService bandCreationService;
  private final AlbumCreationService albumCreationService;
  private final AlbumUpdateService albumUpdateService;
  private final GenreCreationService genreCreationService;
  private final TrackCreationService trackCreationService;

  @PostMapping("/track")
  public ResponseEntity<CreatedTrackDto> create(@RequestBody @Valid TrackDto dto) {
    MusicBand band = dto.getBand() != null ? bandCreationService.createIfNeeded(dto.getBand()) : null;
    MusicAlbum album = dto.getAlbum() != null ? albumCreationService.createIfNeeded(band, dto.getAlbum()) : null;
    Set<MusicGenre> musicGenres = genreCreationService.createGenresIfNeeded(dto.getGenres());
    MusicTrack newTrack = new MusicTrack(dto.getName(), dto.getLength());
    newTrack.setGenres(musicGenres);
    if (band != null) {
      newTrack.setBandName(band.getName());
    }
    if (album != null) {
      newTrack.setAlbumName(album.getName());
      newTrack = albumUpdateService.createTrackInAlbum(album, newTrack);
    } else {
      newTrack = trackCreationService.create(newTrack);
    }
    CreatedTrackDto createdTrackDto = new CreatedTrackDto();
    createdTrackDto.setId(newTrack.getId());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdTrackDto);
  }

}
