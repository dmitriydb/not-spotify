package ru.shanalotte.music.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MusicBand extends BasicEntity {
  private @NonNull String name;
  private Set<MusicAlbum> albums = new HashSet<>();

  public MusicBand(@NonNull long id, @NonNull String name) {
    super(id);
    this.name = name;
  }

}
