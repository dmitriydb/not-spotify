package ru.shanalotte.music.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.music.domain.MusicAlbum;
import ru.shanalotte.music.domain.MusicBand;

@Repository
public interface MusicAlbumRepository extends MongoRepository<MusicAlbum, String> {

}
