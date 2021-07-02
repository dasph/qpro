package me.amvse.qpro.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import me.amvse.qpro.models.User;
import me.amvse.qpro.repositories.UserRepository;
import me.amvse.qpro.security.JwtTokenUtil;
import me.amvse.qpro.security.UserPasswordEncoder;
import me.amvse.qpro.service.JwtUserDetailsService;
import me.amvse.qpro.typings.RestException;
import me.amvse.qpro.typings.SigninSuccess;
import me.amvse.qpro.validators.Signin;
import me.amvse.qpro.validators.Signup;


@RestController
@CrossOrigin
public class AuthenticationController {
	@Value("${me.amvse.qpro.secret}")
  private String secret;

	@Autowired
  private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping("/signin")
	public ResponseEntity<?> createAuthenticationToken (@Valid @RequestBody Signin form, BindingResult br) {
    if (br.hasErrors()) throw new RestException(400, br.getFieldError().getDefaultMessage());

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
		} catch (Exception e) {
			throw new RestException(400, "Invalid credentials");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(form.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new SigninSuccess(token));
	}

	@PostMapping("/signup")
  public void createUser (@Valid @RequestBody Signup form, BindingResult br) {
    if (br.hasErrors()) throw new RestException(400, br.getFieldError().getDefaultMessage());

    if (userRepository.findOneByEmail(form.getEmail()).isPresent()) throw new RestException(400, "User with that email account already exists.");

    String hmac = UserPasswordEncoder.encode(form.getPassword(), secret);
    if (hmac == null) throw new RestException(500, "Failed to hash the provided password.");

    User user = new User(form.getName().trim(), form.getEmail(), hmac);

    userRepository.save(user);
  }
}
