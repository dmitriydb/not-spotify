package ru.shanalotte.music.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MusicTrackTest {

  @Nested
  @DisplayName("Creating tracks v1.0")
  class Creation {

    @Test
    @DisplayName("Creating a music track and toString() with lombok")
    public void create_Track() {
      MusicTrack musicTrack = new MusicTrack(1, "Get away", 30);
      System.out.println(musicTrack);
    }

    @Test
    @DisplayName("Assigning a few genres to a track")
    public void addGenres_toTrack() {
      var track = new MusicTrack(10, "Test", 45);
      track.addGenre(new MusicGenre(1, "breakcore"));
      System.out.println(track.getGenres());
    }

    @Test
    @DisplayName("Created tracks have correct creation time")
    public void check_TrackCreationTime() {
      var track = new MusicTrack(10, "Test", 45);
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
      var track1 = new MusicTrack(1, "Get away", 30);
      var track2 = new MusicTrack(12, "Get away", 30);
      assertEquals(track1, track2);
    }

  }

}