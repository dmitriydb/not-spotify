package ru.shanalotte.music.service.track;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicTrack;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

@Service
@RequiredArgsConstructor
public class TrackCreationService {

  private final MusicTrackRepository musicTrackRepository;

  public MusicTrack create(MusicTrack track) {
    return musicTrackRepository.save(track);
  }
}
