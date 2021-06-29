package me.amvse.qpro.controllers;

import me.amvse.qpro.models.User;
import me.amvse.qpro.repositories.UserRepository;
import me.amvse.qpro.typings.RestException;
import me.amvse.qpro.validators.Signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;

@RestController
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @Value("${me.amvse.qpro.secret}")
  private String secret;

  // ------------- CRUD -------------

  @GetMapping("/users")
  public Page<User> getUsers (Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @GetMapping("/users/{userId}")
  public User getUser (@PathVariable Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RestException(404, "User not found with id " + userId));
  }

  @PostMapping("/users")
  public User createUser (@Valid @RequestBody Signup form, BindingResult br) {
    if (br.hasErrors()) throw new RestException(400, br.getFieldError().getDefaultMessage());

    if (userRepository.findOneByEmail(form.getEmail()).isPresent()) throw new RestException(400, "User with that email account already exists.");

    String hmac = hashPass(form.getPassword());
    if (hmac == null) throw new RestException(500, "Failed to hash the provided password.");

    User user = new User(form.getName().trim(), form.getEmail(), hmac);

    return userRepository.save(user);
  }

  @PutMapping("/users/{userId}")
  public User updateUser (@PathVariable Long userId, @Valid @RequestBody Signup form) {
    return userRepository.findById(userId).map((user) -> {
      String hmac = hashPass(form.getPassword());
      if (hmac == null) throw new RestException(500, "Failed to hash the provided password.");

      user.setEmail(form.getEmail());
      user.setName(form.getName().trim());
      user.setHmac(hmac);

      return userRepository.save(user);
    }).orElseThrow(() -> new RestException(404, "User not found with id " + userId));
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<?> deleteUser (@PathVariable Long userId) {
    return userRepository.findById(userId).map((user) -> {
      userRepository.delete(user);
      return ResponseEntity.ok().build();
    }).orElseThrow(() -> new RestException(404, "User not found with id " + userId));
  }

  // ------------- API -------------

  // TODO: JWT

  // TODO: Signup
  // TODO: Signin
  // TODO: Get Account
  // TODO: Update Account
  // TODO: Delete Account

  private String hashPass (String pass) {
    SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

    try {
      Mac hmac = Mac.getInstance("HmacSHA256");
      hmac.init(secretKeySpec);
      byte[] hmacData = hmac.doFinal(pass.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(hmacData);

    } catch (Exception e) { return null; }
  }
}
