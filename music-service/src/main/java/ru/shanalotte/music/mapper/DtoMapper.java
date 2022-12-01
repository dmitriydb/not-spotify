package ru.shanalotte.music.mapper;

public interface DtoMapper<DTO, ENTITY> {
  DTO toDto(ENTITY entity);
}
