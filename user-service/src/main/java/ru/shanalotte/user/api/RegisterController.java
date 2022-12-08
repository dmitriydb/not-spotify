package ru.shanalotte.user.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.user.dto.RegistrationDto;
import ru.shanalotte.user.domain.User;
import ru.shanalotte.user.repository.UserRepository;
import ru.shanalotte.user.service.JwtTokenProvider;

@RestController
public class RegisterController {

  private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

  private final JwtTokenProvider jwtTokenProvider;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public RegisterController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegistrationDto requestDto) {
    logger.info("Registration attempt {}", requestDto.getUsername());
      String username = requestDto.getUsername();
      User existingUser = userRepository.findByUsername(username);
      if (existingUser != null) {
        logger.info("User {} is already registered", requestDto.getUsername());
        logger.info("{}", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
      String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
      logger.debug("Encoded password is {}", encodedPassword);
      User user = new User(UUID.randomUUID().toString(), requestDto.getUsername(), encodedPassword);
      userRepository.save(user);
      String token = jwtTokenProvider.createToken(username);
      Map<Object, Object> response = new HashMap<>();
      response.put("username", username);
      response.put("token", token);
      logger.info("Successfully created JWT token for {}", requestDto.getUsername());
      return ResponseEntity.ok(response);
  }
}
