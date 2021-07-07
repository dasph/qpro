package me.amvse.qpro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientRedirectController {
  @GetMapping(value = "/**/{path:[^\\.]*}")
  public String redirect () {
    return "redirect:/";
  }
}
