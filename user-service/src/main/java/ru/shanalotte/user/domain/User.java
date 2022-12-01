package ru.shanalotte.user.domain;

import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("spotify_users")
public class User {

  public User(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public User() {
  }

  @Id
  private String id;
  private String username;
  private String password;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id=").append(id);
    sb.append(", username='").append(username).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
