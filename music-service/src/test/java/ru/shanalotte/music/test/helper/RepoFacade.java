package ru.shanalotte.music.test.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.api.internal.TrackUploadController;
import ru.shanalotte.music.persistence.repository.MusicAlbumRepository;
import ru.shanalotte.music.persistence.repository.MusicBandRepository;
import ru.shanalotte.music.persistence.repository.MusicGenreRepository;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

@Service
@Profile("test")
public class RepoFacade {

  @Autowired
  private MusicAlbumRepository musicAlbumRepository;

  @Autowired
  private MusicBandRepository musicBandRepository;

  @Autowired
  private MusicGenreRepository musicGenreRepository;

  @Autowired
  private MusicTrackRepository musicTrackRepository;

  @Autowired
  private TrackUploadController trackController;

  public void deleteAll() {
    musicAlbumRepository.deleteAll();
    musicBandRepository.deleteAll();
    musicGenreRepository.deleteAll();
    musicTrackRepository.deleteAll();
  }


}
