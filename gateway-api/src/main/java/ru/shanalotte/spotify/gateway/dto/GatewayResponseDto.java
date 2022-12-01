package ru.shanalotte.spotify.gateway.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GatewayResponseDto {
  private @NonNull GatewayResponseStatus status;
  private @NonNull Object payload;
}
