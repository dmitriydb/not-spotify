package ru.shanalotte.music.test.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.persistence.repository.MusicBandRepository;

@Service
public class BandHelper extends TestHelper {

  @Autowired
  private MusicBandRepository musicBandRepository;

  public boolean bandExists(String bandName) {
    return musicBandRepository.findByName(bandName) != null;
  }

  public MusicBand band(String bandName) {
    return musicBandRepository.findByName(bandName);
  }

  public int bandsTotal() {
    return musicBandRepository.findAll().size();
  }
}
