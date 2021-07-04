package me.amvse.qpro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import me.amvse.qpro.forms.SignupForm;
import me.amvse.qpro.service.SecurityService;
import me.amvse.qpro.service.UserService;
import me.amvse.qpro.validators.SignupFormValidator;

@Controller
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private SignupFormValidator signupFormValidator;

  @GetMapping("/signup")
  public String signup (Model model) {
    if (securityService.isAuthenticated()) return "redirect:/";

    model.addAttribute("signupForm", new SignupForm());

    return "signup";
  }

  @PostMapping("/signup")
  public String signup (@ModelAttribute("signupForm") SignupForm signupForm, BindingResult bindingResult) {
    signupFormValidator.validate(signupForm, bindingResult);
    if (bindingResult.hasErrors()) return "signup";

    userService.create(signupForm);

    securityService.autoLogin(signupForm.getEmail(), signupForm.getPassword());

    return "redirect:/";
  }

  @GetMapping("/signin")
  public String signin (Model model, String error, String logout) {
    if (securityService.isAuthenticated()) return "redirect:/";

    if (error != null) model.addAttribute("error", "Invalid credentials.");

    if (logout != null) model.addAttribute("message", "You have been successfully logged out.");

    return "signin";
  }

  @GetMapping({"/"})
  public String home (Model model) {
    return "home";
  }
}
