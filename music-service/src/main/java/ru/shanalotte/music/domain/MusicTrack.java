package ru.shanalotte.music.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MusicTrack extends BasicEntity {

  public MusicTrack(@NonNull long id, @NonNull String name, @NonNull int length) {
    super(id);
    this.name = name;
    this.length = length;
  }

  private @NonNull String name;
  private @NonNull int length;
  private Set<MusicGenre> genres = new HashSet<>();


  public void addGenre(MusicGenre genre) {
    this.genres.add(genre);
  }

  public void addGenres(Collection<MusicGenre> genres) {
    this.genres.addAll(genres);
  }


}
