package ru.shanalotte.music.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import com.mongodb.lang.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("music_tracks")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MusicTrack extends BasicEntity {

  private @NonNull String name;
  private @NonNull int length;
  private String bandName;
  private String albumName;
  private String filePath;
  private String albumCover;

  @DocumentReference(collection = "music_genres")
  private Set<MusicGenre> genres = new HashSet<>();

  public void addGenre(MusicGenre genre) {
    this.genres.add(genre);
  }

  public void addGenres(Collection<MusicGenre> genres) {
    this.genres.addAll(genres);
  }


}
