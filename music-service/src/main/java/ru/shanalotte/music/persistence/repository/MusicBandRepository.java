package ru.shanalotte.music.persistence.repository;

import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.music.domain.MusicBand;
import ru.shanalotte.music.domain.MusicTrack;

@Repository
public interface MusicBandRepository extends MongoRepository<MusicBand, String> {

  MusicBand findByName(String name);

}
