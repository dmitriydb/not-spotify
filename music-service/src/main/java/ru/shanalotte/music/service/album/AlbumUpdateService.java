package ru.shanalotte.music.service.album;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.domain.MusicTrack;
import ru.shanalotte.music.dto.TrackDto;
import ru.shanalotte.music.persistence.repository.MusicAlbumRepository;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumUpdateService {

  private final MusicAlbumRepository musicAlbumRepository;
  private final MusicTrackRepository musicTrackRepository;
  private final MongoTemplate mongoTemplate;

  public MusicTrack createTrackInAlbum(MusicAlbum album, MusicTrack newTrack) {
    log.debug("createTrackInAlbum");
    log.debug("album songs {}", album.getTracks());
    if (album.hasTrack(newTrack)) {
      log.debug("album has track");
      MusicTrack existingTrack = album.getTracks().stream()
          .filter(t -> t.equals(newTrack))
          .findFirst()
          .get();
      existingTrack.addGenres(newTrack.getGenres());
      existingTrack.setFilePath(newTrack.getFilePath());
      existingTrack.setAlbumCover(newTrack.getAlbumCover());
      log.debug("Track has following genres {} before update", newTrack.getGenres());
      mongoTemplate.save(existingTrack);
      return existingTrack;
    } else {
      log.debug("add track");
      album.addTrack(newTrack);
      mongoTemplate.save(album);
      log.debug("Track has following genres {} before save", newTrack.getGenres());
      musicTrackRepository.save(newTrack);
      return newTrack;
    }
  }

  public MusicAlbum setAlbumCover(MusicAlbum album, String cover) {
    album.setAlbumCover(cover);
    return musicAlbumRepository.save(album);
  }

}
