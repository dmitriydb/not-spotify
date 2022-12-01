package ru.shanalotte.music.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BandDto {
  private @NonNull String id;
  private @NonNull String name;
}
