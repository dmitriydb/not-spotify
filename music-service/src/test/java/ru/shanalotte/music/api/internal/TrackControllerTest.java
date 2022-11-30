package ru.shanalotte.music.api.internal;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.domain.MusicGenre;
import ru.shanalotte.music.domain.MusicTrack;
import ru.shanalotte.music.dto.TrackDto;
import ru.shanalotte.music.test.annotation.IntegrationTest;
import ru.shanalotte.music.test.helper.AlbumHelper;
import ru.shanalotte.music.test.helper.BandHelper;
import ru.shanalotte.music.test.helper.GenreHelper;
import ru.shanalotte.music.test.helper.RepoFacade;
import ru.shanalotte.music.test.helper.TrackHelper;

@DisplayName("Testing internal API for loading tracks to music service")
@ActiveProfiles("test")
@SpringBootTest
public class TrackControllerTest {

  @Autowired
  private RepoFacade repoFacade;

  @Autowired
  private BandHelper bandHelper;

  @Autowired
  private AlbumHelper albumHelper;

  @Autowired
  private TrackHelper trackHelper;

  @Autowired
  private GenreHelper genreHelper;

  @Autowired
  private TrackController trackController;

  @AfterEach
  public void dropDatabase() {
    repoFacade.deleteAll();
  }

  @Nested
  class Creation {

    @IntegrationTest
    @DisplayName("Creating a track from the scratch with all fields filled")
    public void should_Create() {
      TrackDto trackDto = new TrackDto("Get away", 330, Set.of("pop"), "Get away album", "Band name");
      trackController.create(trackDto);
      assertTrue(bandHelper.bandExists(trackDto.getBand()));
      MusicBand musicBand = bandHelper.band(trackDto.getBand());
      assertThat(musicBand.getAlbums().size()).isEqualTo(1);
      assertThat(musicBand.getAlbums().iterator().next().getTracks().size()).isEqualTo(1);
    }

    @IntegrationTest
    @DisplayName("Saving an album with three songs")
    public void should_Create_AlbumWithThreeSongs() {
      List<TrackDto> dtos = List.of(
          new TrackDto("Song name1", 335, Set.of("rock"), "First album", "Beatles"),
          new TrackDto("Song name2", 335, Set.of("rock"), "First album", "Beatles"),
          new TrackDto("Song name3", 335, Set.of("rock"), "First album", "Beatles")
      );

      dtos.forEach(trackController::create);
      assertThat(bandHelper.bandsTotal()).isEqualTo(1);
      MusicBand musicBand = bandHelper.band("Beatles");
      assertThat(musicBand.getAlbums().size()).isEqualTo(1);
      assertThat(musicBand.getAlbums().iterator().next().getTracks().size()).isEqualTo(3);
    }

    @IntegrationTest
    @DisplayName("Fetching song data")
    public void should_Save_Song_Details() {
      TrackDto dto = new TrackDto("Song name1", 123, Set.of("rock", "pop", "disco"), "First album", "Beatles");
      String newId = trackController.create(dto).getBody().getId();
      assertThat(bandHelper.bandsTotal()).isEqualTo(1);
      MusicBand musicBand = bandHelper.band("Beatles");
      assertThat(musicBand.getAlbums().size()).isEqualTo(1);
      assertTrue(genreHelper.genreExists("rock"));
      assertTrue(genreHelper.genreExists("pop"));
      assertTrue(genreHelper.genreExists("disco"));
      MusicTrack singleTrack = musicBand.getAlbums().iterator().next().getTracks().iterator().next();
      assertThat(singleTrack.getId()).isEqualTo(newId);
      assertThat(singleTrack.getName()).isEqualTo(dto.getName());
      assertThat(singleTrack.getAlbumName()).isEqualTo(dto.getAlbum());
      assertThat(singleTrack.getBandName()).isEqualTo(dto.getBand());
      assertThat(singleTrack.getGenres().stream().map(MusicGenre::getName).collect(Collectors.toList())).asList().contains("rock", "pop", "disco");
    }

    @IntegrationTest
    @DisplayName("Saving a bunch of songs without band and album")
    public void should_createSongsWithoutAlbumAndBand() {
      List<TrackDto> dtos = List.of(
          new TrackDto("Song name1", 335, Set.of("rock") ),
          new TrackDto("Song name2", 335, Set.of("rock", "pop") ),
          new TrackDto("Song name3", 335, Set.of("breakcore", "disco"))
      );
      dtos.forEach(trackController::create);
      assertThat(bandHelper.bandsTotal()).isEqualTo(0);
      assertThat(albumHelper.albumsTotal()).isEqualTo(0);
      assertThat(trackHelper.tracksTotal()).isEqualTo(3);
    }

    @IntegrationTest
    @DisplayName("Complex test case")
    public void should_correctlyCreate() {
      List<TrackDto> dtos = List.of(
          new TrackDto("T1", 335, Set.of("rock"), "A1", "Beatles"),
          new TrackDto("T2", 335, Set.of("rock"), null, "Beatles"),
          new TrackDto("T2", 335, Set.of("rock"), "A1", "Eagles"),
          new TrackDto("T1", 335, Set.of("rock"), "A1", "Eagles"),
          new TrackDto("T1", 335, Set.of("rock"), "A1", null),
          new TrackDto("T1", 335, Set.of("rock"), "A1", null),
          new TrackDto("T1", 335, Set.of("rock"), null, null),
          new TrackDto("T2", 335, Set.of("rock"), null, null)
      );
      dtos.forEach(trackController::create);
      assertThat(bandHelper.bandsTotal()).isEqualTo(2);
      assertThat(albumHelper.albumsTotal()).isEqualTo(4);
      assertThat(trackHelper.tracksTotal()).isEqualTo(8);
    }
  }

}