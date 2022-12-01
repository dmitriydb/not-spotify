package ru.shanalotte.music.api.external;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.music.dto.GenreDto;
import ru.shanalotte.music.mapper.GenreDtoMapper;
import ru.shanalotte.music.persistence.repository.MusicGenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

  private final MusicGenreRepository musicGenreRepository;
  private final GenreDtoMapper genreDtoMapper;

  @GetMapping("/genre")
  public List<GenreDto> allGenres() {
    return musicGenreRepository.findAll()
        .stream().map(genreDtoMapper::toDto)
        .collect(Collectors.toList());
  }

}
