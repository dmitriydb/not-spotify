package ru.shanalotte.music.service.album;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.persistence.repository.MusicAlbumRepository;
import ru.shanalotte.music.persistence.repository.MusicBandRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumCreationService {
  private final MusicAlbumRepository musicAlbumRepository;
  private final MusicBandRepository musicBandRepository;

  public MusicAlbum createIfNeeded(MusicBand band, String albumName) {
    log.debug("Band {} albumName {}", band, albumName);

    if (band != null) {
      log.debug("Band albums {}", band.getAlbums());
      Optional<MusicAlbum> foundAlbum = band.getAlbums().stream().filter(a -> a.getName().equals(albumName)).findFirst();
      if (foundAlbum.isPresent()) {
        return foundAlbum.get();
      }
    }
    if (albumName != null) {
      log.debug("Creating album");
      MusicAlbum newAlbum = new MusicAlbum(albumName);
      musicAlbumRepository.save(newAlbum);
      if (band != null) {
        band.addAlbum(newAlbum);
        musicBandRepository.save(band);
      }
      return newAlbum;
    }
    else {
      return null;
    }
  }


}
