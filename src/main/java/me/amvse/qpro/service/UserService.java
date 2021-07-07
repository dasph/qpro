package me.amvse.qpro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import me.amvse.qpro.forms.SignupForm;
import me.amvse.qpro.models.User;
import me.amvse.qpro.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public void create (SignupForm form) {
    String pass = bCryptPasswordEncoder.encode(form.getPassword());
    User user = new User(form.getName().trim(), form.getEmail(), pass);

    userRepository.save(user);
  }

  public Optional<User> findByEmail (String email) {
    return userRepository.findOneByEmail(email);
  }
}
