package me.amvse.qpro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
	@GetMapping("/")
	public String signin () {
		return "signin";
	}
}
