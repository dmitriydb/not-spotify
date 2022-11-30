package ru.shanalotte.music.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import ru.shanalotte.music.persistence.repository.MusicGenreRepository;
import ru.shanalotte.test.annotation.IntegrationTest;

@SpringBootTest
public class MusicGenreTest {

  @Nested
  @DisplayName("Persisting genres with Mongo DB")
  class Persisting {

    @Autowired
    private MusicGenreRepository musicGenreRepository;

    @DisplayName("Saving genre in local mongo instance and deleting it")
    @IntegrationTest
    public void should_Save() {
      var musicGenre = new MusicGenre("breakcore");
      musicGenreRepository.save(musicGenre);
      assertEquals(musicGenreRepository.findByName("breakcore").stream().findFirst().get().getId(), musicGenre.getId());
      musicGenreRepository.deleteByName("breakcore");
      assertTrue(musicGenreRepository.findByName("breakcore").stream().findFirst().isEmpty());
    }

    @DisplayName("Should not create two breakcore genres together")
    @IntegrationTest
    public void should_ThrowError_IfUniqueIndexConstraintIsViolated() {
      var breakcore = new MusicGenre("breakcore");
      var breakcoreAgain = new MusicGenre("breakcore");
      musicGenreRepository.save(breakcore);
      assertThrows(DuplicateKeyException.class, () -> musicGenreRepository.save(breakcoreAgain));
      musicGenreRepository.deleteByName("breakcore");
    }

  }

}