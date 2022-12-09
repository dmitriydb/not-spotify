package ru.shanalotte.music.test;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.persistence.repository.MusicAlbumRepository;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

@SpringBootTest
public class AlbumCoverFixer {

  @Autowired
  MusicAlbumRepository musicAlbumRepository;
  MusicTrackRepository musicTrackRepository;

  @Test
  void test() {
    List<MusicAlbum> albums = musicAlbumRepository.findAll();
    for (var album : albums) {
      System.out.println(album.getAlbumCover());
      for (var song: album.getTracks()) {
        System.out.println("-----" + song.getAlbumCover() + ":" + song.getFilePath());
      }
    }
    while (true) {
      
    }
  }
}
