package ru.shanalotte.music.persistence.repository;

import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.music.domain.MusicAlbum;

@Repository
public interface MusicAlbumRepository extends MongoRepository<MusicAlbum, String> {
  Set<MusicAlbum> findByName(String name);
}
