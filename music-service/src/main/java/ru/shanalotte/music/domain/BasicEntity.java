package ru.shanalotte.music.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class BasicEntity {

  @Id
  protected String id = UUID.randomUUID().toString();
  protected LocalDateTime createdAt = LocalDateTime.now();

  public BasicEntity(@NonNull String id) {
    this.id = id;
  }
}
