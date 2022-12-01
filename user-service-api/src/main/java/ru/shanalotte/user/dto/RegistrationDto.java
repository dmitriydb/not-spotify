package ru.shanalotte.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RegistrationDto {
  private @NonNull @NotNull @NotEmpty String username;
  private @NonNull @NotNull @NotEmpty String password;
}
