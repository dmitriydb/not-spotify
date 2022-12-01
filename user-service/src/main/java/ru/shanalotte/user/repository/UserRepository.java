package ru.shanalotte.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.user.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  User findByUsername(String username);
}
