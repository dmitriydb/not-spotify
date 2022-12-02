package ru.shanalotte.music.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TrackDto {

  public TrackDto(String name, int length, Set<String> genres, String album, String band) {
    this.name = name;
    this.length = length;
    this.genres = genres;
    this.album = album;
    this.band = band;
  }

  public TrackDto(String name, int length, Set<String> genres) {
    this.name = name;
    this.length = length;
    this.genres = genres;
    this.album = null;
    this.band = null;
  }

  private String id;

  @NotNull
  private String name;

  @NotNull
  private int length;

  private Set<String> genres = new HashSet<>();

  private String album;

  private String band;

  private String albumCover;

  private String mp3File;
}
