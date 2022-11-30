package ru.shanalotte.music.service.band;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.persistence.repository.MusicBandRepository;

@Service
@RequiredArgsConstructor
public class BandUpdateService {

  private final MusicBandRepository musicBandRepository;

  public void addAlbumToBand(MusicBand band, MusicAlbum album) {
    band.addAlbum(album);
    musicBandRepository.save(band);
  }

}
