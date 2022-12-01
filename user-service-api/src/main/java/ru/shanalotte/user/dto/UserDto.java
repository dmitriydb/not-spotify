package ru.shanalotte.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserDto {

  public UserDto(String id) {
    this.id = id;
  }

  private String id;
}
