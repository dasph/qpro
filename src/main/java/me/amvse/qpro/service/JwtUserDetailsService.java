package me.amvse.qpro.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import me.amvse.qpro.repositories.UserRepository;
import me.amvse.qpro.typings.RestException;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername (String email) throws RestException {
		me.amvse.qpro.models.User user = userRepository.findOneByEmail(email).orElseThrow(() -> new RestException(404, "User not found"));

		return new User(user.getEmail(), user.getHmac(), new ArrayList<>());
	}
}
