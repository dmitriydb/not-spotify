package ru.shanalotte.music.test.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.persistence.repository.MusicAlbumRepository;

@Service
public class AlbumHelper extends TestHelper {

  @Autowired
  private MusicAlbumRepository musicAlbumRepository;

  public boolean albumExists(String albumName) {
    return musicAlbumRepository.findByName(albumName).size() > 0;
  }

  public int albumsTotal() {
    return musicAlbumRepository.findAll().size();
  }
}
