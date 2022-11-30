package ru.shanalotte.music.test.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.persistence.repository.MusicBandRepository;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

@Service
public class TrackHelper extends TestHelper {

  @Autowired
  private MusicTrackRepository musicTrackRepository;

  public int tracksTotal() {
    return musicTrackRepository.findAll().size();
  }
}
