package ru.shanalotte.user.api;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.user.domain.User;
import ru.shanalotte.user.dto.AuthDto;
import ru.shanalotte.user.dto.UserDto;
import ru.shanalotte.user.repository.UserRepository;
import ru.shanalotte.user.service.JwtTokenProvider;

@RestController
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/user/{username}")
  public ResponseEntity<UserDto> getUserId(@PathVariable("username") String username) {
    User user = userRepository.findByUsername(username);
    if (user != null) {
      return ResponseEntity.ok(new UserDto(user.getId()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
