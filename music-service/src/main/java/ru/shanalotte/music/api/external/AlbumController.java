package ru.shanalotte.music.api.external;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.music.dto.AlbumDto;
import ru.shanalotte.music.mapper.AlbumDtoMapper;
import ru.shanalotte.music.persistence.repository.MusicAlbumRepository;

@RestController
@RequiredArgsConstructor
public class AlbumController {

  private final MusicAlbumRepository musicAlbumRepository;
  private final AlbumDtoMapper AlbumDtoMapper;

  @GetMapping("/album")
  public List<AlbumDto> allAlbums() {
    return musicAlbumRepository.findAll()
        .stream().map(AlbumDtoMapper::toDto)
        .collect(Collectors.toList());
  }

}
