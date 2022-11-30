package ru.shanalotte.music.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MusicGenre extends BasicEntity {
  private @NonNull String name;

  public MusicGenre(@NonNull long id, @NonNull String name) {
    super(id);
    this.name = name;
  }

}
