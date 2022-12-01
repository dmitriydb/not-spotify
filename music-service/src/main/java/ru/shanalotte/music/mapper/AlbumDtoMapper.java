package ru.shanalotte.music.mapper;

import org.springframework.stereotype.Service;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.dto.AlbumDto;
import ru.shanalotte.music.dto.BandDto;

@Service
public class AlbumDtoMapper implements DtoMapper<AlbumDto, MusicAlbum> {

  @Override
  public AlbumDto toDto(MusicAlbum album) {
    return new AlbumDto(album.getId(), album.getName());
  }

}
