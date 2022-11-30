package ru.shanalotte.music.persistence.repository;

import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.music.domain.MusicGenre;

@Repository
public interface MusicGenreRepository extends MongoRepository<MusicGenre, String> {
  MusicGenre findByName(String name);
  void deleteByName(String name);
}
