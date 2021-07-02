package me.amvse.qpro.validators;

import javax.validation.constraints.NotBlank;

public class Signin {
  @NotBlank(message = "Please provide an email")
	private String email;

  @NotBlank(message = "Please provide a password")
	private String password;

	public Signin () {}
	public Signin (String email, String password) { this.email = email; this.password = password; }

	public String getEmail () { return this.email; }
	public void setEmail (String email) { this.email = email; }

	public String getPassword () { return this.password; }
	public void setPassword (String password) { this.password = password; }
}
