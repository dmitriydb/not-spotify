package ru.shanalotte.music.service.band;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.persistence.repository.MusicBandRepository;

@Service
@RequiredArgsConstructor
public class BandCreationService {

  private final MusicBandRepository musicBandRepository;

  public MusicBand createIfNeeded(String bandName) {
    MusicBand existingBand = musicBandRepository.findByName(bandName);
    if (existingBand != null) {
      return existingBand;
    }
    MusicBand newBand = new MusicBand(bandName);
    return musicBandRepository.save(newBand);
  }

}
