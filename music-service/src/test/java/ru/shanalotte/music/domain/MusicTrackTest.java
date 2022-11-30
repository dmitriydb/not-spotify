package ru.shanalotte.music.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Queue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import ru.shanalotte.music.persistence.repository.MusicGenreRepository;
import ru.shanalotte.music.persistence.repository.MusicTrackRepository;

public class MusicTrackTest {

  @Nested
  @DisplayName("Creating tracks v1.0")
  class Creation {

    @Test
    @DisplayName("Creating a music track and toString() with lombok")
    public void create_Track() {
      MusicTrack musicTrack = new MusicTrack( "Get away", 30);
      System.out.println(musicTrack);
    }

    @Test
    @DisplayName("Assigning a few genres to a track")
    public void addGenres_toTrack() {
      var track = new MusicTrack("Test", 45);
      track.addGenre(new MusicGenre("breakcore"));
      System.out.println(track.getGenres());
    }

    @Test
    @DisplayName("Created tracks have correct creation time")
    public void check_TrackCreationTime() {
      var track = new MusicTrack( "Test", 45);
      Duration durationBetweenCreation = Duration.between(track.getCreatedAt(), LocalDateTime.now());
      assertThat(durationBetweenCreation.toMillis()).isLessThan(10);
    }
  }

  @Nested
  @DisplayName("Comparing tracks v 1.0")
  class Comparing {

    @Test
    @DisplayName("Equal tracks with different ids should be equal")
    public void comparing_equalTracks_withDifferentId() {
      var track1 = new MusicTrack( "Get away", 30);
      var track2 = new MusicTrack("Get away", 30);
      assertEquals(track1, track2);
    }

  }

  @Nested
  @DisplayName("Persisting tracks in local mongo")
  @SpringBootTest
  class Persisting {

    @Autowired
    private MusicTrackRepository musicTrackRepository;

    @Autowired
    private MusicGenreRepository musicGenreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Persisting tracks along with its genres")
    public void should_saveTracksAndAlsoGenres() {
      musicGenreRepository.deleteByName("breakcore");
      musicGenreRepository.deleteByName("hardcore");
      var genre1 = new MusicGenre("breakcore");
      var genre2 = new MusicGenre("hardcore");
      var track = new MusicTrack("Test track", 100);
      var genres = List.of(genre1, genre2);
      track.addGenres(genres);
      musicTrackRepository.save(track);
      genres.forEach(genre -> musicGenreRepository.save(genre));
      Query query = new Query();
      query.addCriteria(Criteria.where("name").is("breakcore").orOperator(Criteria.where("name").is("breakcore")));
      List<MusicGenre> musicGenres = mongoTemplate.find(query, MusicGenre.class);
      assertThat(track.getGenres()).containsAll(musicGenres);
      musicTrackRepository.deleteById(track.getId());
    }

  }

}