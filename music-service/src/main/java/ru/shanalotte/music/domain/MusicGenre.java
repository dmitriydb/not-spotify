package ru.shanalotte.music.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("music_genres")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MusicGenre extends BasicEntity {

  @NonNull
  @Indexed(unique = true)
  private String name;

}
