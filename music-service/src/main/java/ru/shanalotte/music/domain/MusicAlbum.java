package ru.shanalotte.music.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("music_albums")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MusicAlbum extends BasicEntity {

  private @NonNull String name;

  @DocumentReference(collection = "music_tracks")
  private Set<MusicTrack> tracks = new HashSet<>();

  public void addTrack(MusicTrack track) {
    this.tracks.add(track);
  }

  public boolean hasTrack(MusicTrack track) {
    return this.tracks.contains(track);
  }
}
