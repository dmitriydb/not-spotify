package ru.shanalotte.music.test.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.persistence.repository.MusicAlbumRepository;
import ru.shanalotte.music.persistence.repository.MusicGenreRepository;

@Service
public class GenreHelper extends TestHelper {

  @Autowired
  private MusicGenreRepository musicGenreRepository;

  public boolean genreExists(String genreName) {
    return musicGenreRepository.findByName(genreName) != null;
  }

  public int genresTotal() {
    return musicGenreRepository.findAll().size();
  }
}
