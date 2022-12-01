package ru.shanalotte.music.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import ru.shanalotte.music.persistence.repository.MusicGenreRepository;
import ru.shanalotte.music.test.annotation.IntegrationTest;

@SpringBootTest
@ActiveProfiles("test")
public class MusicGenreTest {

  @Nested
  @DisplayName("Comparing")
  class Comparing {

    @DisplayName("Two genres with different id should be equal")
    @Test
    public void should_be_Equal() {
      var genre1 = new MusicGenre("rock");
      var genre2 = new MusicGenre("rock");
      assertEquals(genre1, genre2);
      assertNotEquals(genre1.getId(), genre2.getId());
    }
  }

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
      assertEquals(musicGenreRepository.findByName("breakcore").getId(), musicGenre.getId());
      musicGenreRepository.deleteByName("breakcore");
      assertNull(musicGenreRepository.findByName("breakcore"));
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