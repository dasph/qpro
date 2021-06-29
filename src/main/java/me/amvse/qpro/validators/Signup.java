package me.amvse.qpro.validators;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import me.amvse.qpro.constraints.ValidPassword;

public class Signup {
	@Size(min = 1, max = 64, message = "Your name has to be between 1 and 64 characters in length")
  @NotBlank(message = "Please provide a name")
  private String name;

  @NotBlank(message = "Please provide an email")
  @Email(message = "Provided email is not valid")
  private String email;

  @ValidPassword
  private String password;

  public String getName () { return this.name; }
  public void setName (String name) { this.name = name; }

  public String getEmail () { return this.email; }
  public void setEmail (String email) { this.email = email; }

  public String getPassword () { return this.password; }
  public void setPassword (String password) { this.password = password; }
}
