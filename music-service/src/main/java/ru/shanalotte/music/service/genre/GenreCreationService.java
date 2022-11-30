package ru.shanalotte.music.service.genre;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicGenre;
import ru.shanalotte.music.persistence.repository.MusicGenreRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreCreationService {

  private final MusicGenreRepository musicGenreRepository;

  public Set<MusicGenre> createGenresIfNeeded(Set<String> genres) {
    if (genres == null) {
      return new HashSet<>();
    }
    return genres.stream().map(
        this::createOrGet
    ).collect(Collectors.toSet());
  }

  private MusicGenre createOrGet(String genreName) {
    MusicGenre genre = musicGenreRepository.findByName(genreName);
    if (genre != null) {
      return genre;
    }
    log.debug("Saving genre {}", genreName);
    return musicGenreRepository.save(new MusicGenre(genreName));
  }

}
