package ru.shanalotte.scanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import ru.shanalotte.music.dto.TrackDto;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@ComponentScan("ru.shanalotte.scanner")
@RequiredArgsConstructor
public class ScannerLauncher implements CommandLineRunner {

  private final MusicServiceProxy musicServiceProxy;

  public static void main(String[] args) throws InvalidDataException, UnsupportedTagException, IOException {
    SpringApplication.run(ScannerLauncher.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    args = new String[]{"D:\\songs"};
    if (args.length == 0) {
      sayBye();
    }
    Scanner in = new Scanner(System.in);
    String rootDirectory = args[0];
    File file = new File(rootDirectory);
    if (!file.isDirectory()) {
      sayBye();
    }
    var dtos = toMp3Files(file)
        .stream().map(ScannerLauncher::toTrackDto)
        .collect(Collectors.toList());

    dtos.forEach(dto -> System.out.println(prettify(dto)));

    System.out.println("y/n?");

    String confirm = in.nextLine();
    if (confirm.equals("y")) {
      dtos.forEach(musicServiceProxy::createTrack);
    }


  }

  private String prettify(TrackDto dto) {
    return String.format("%50s %50s %50s %50s %50s %10s %50s\n", dto.getBand(), dto.getAlbum(), dto.getName(), dto.getGenres(), dto.getLength(), dto.getAlbumCover(), dto.getMp3File());
  }

  private static Set<File> toMp3Files(File rootDirectory) {
    Set<File> files = new HashSet<>();
    Queue<File> subDirectories = new LinkedList<>();
    subDirectories.add(rootDirectory);
    while (!subDirectories.isEmpty()) {
      File directory = subDirectories.poll();
      for (File subFile : directory.listFiles()) {
        if (subFile.isDirectory()) {
          subDirectories.add(subFile);
        } else if (subFile.getAbsolutePath().endsWith(".mp3")){
          files.add(subFile);
        }
      }
    }
    return files;
  }

  @SneakyThrows
  private static TrackDto toTrackDto(File file) {
    Mp3File mp3File = new Mp3File(file);
    TrackDto dto = new TrackDto();
    var tag2 = mp3File.getId3v2Tag();
    if (tag2 != null && tag2.getAlbumImage() != null) {
      String albumCoverPath = "D:\\covers\\" + UUID.randomUUID() + ".jpg";
      try (FileOutputStream fos = new FileOutputStream(albumCoverPath)) {
        fos.write(tag2.getAlbumImage());
      }
      dto.setAlbumCover(albumCoverPath);
    }
    var tag = mp3File.getId3v1Tag();
    if (tag == null) {
      tag = mp3File.getId3v2Tag();
    }
    if (tag != null) {
      dto.setName(tag.getTitle());
      dto.setAlbum(tag.getAlbum());
      dto.setBand(tag.getArtist());
      Set<String> genres = Arrays.stream(tag.getGenreDescription().split("/")).map(genre -> genre.trim()).collect(Collectors.toSet());
      dto.setGenres(genres);
      dto.setLength((int) mp3File.getLengthInSeconds());
    }
    if (dto.getName() == null) {
      dto.setName(file.getName().replace(".mp3", ""));
    }
    dto.setMp3File(file.getAbsolutePath());
    return dto;
  }

  private static void sayBye() {
    System.out.println("Usage: scanner <root directory>");
    System.exit(0);
  }

}

