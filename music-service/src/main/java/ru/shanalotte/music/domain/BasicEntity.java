package ru.shanalotte.music.domain;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class BasicEntity {

  protected @NonNull long id;
  protected LocalDateTime createdAt = LocalDateTime.now();

  public BasicEntity(@NonNull long id) {
    this.id = id;
  }

}
