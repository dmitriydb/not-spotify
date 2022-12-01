package ru.shanalotte.music.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
public class CreatedTrackDto {
  @NonNull String id;
}
