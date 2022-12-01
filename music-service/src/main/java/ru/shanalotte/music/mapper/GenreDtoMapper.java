package ru.shanalotte.music.mapper;

import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.domain.MusicGenre;
import ru.shanalotte.music.dto.AlbumDto;
import ru.shanalotte.music.dto.GenreDto;

@Service
public class GenreDtoMapper implements DtoMapper<GenreDto, MusicGenre> {

  @Override
  public GenreDto toDto(MusicGenre genre) {
    return new GenreDto(genre.getId(), genre.getName());
  }
}
