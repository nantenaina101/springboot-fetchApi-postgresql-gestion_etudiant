package mg.nantenaina.dev.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import mg.nantenaina.dev.config.SecurityConfiguration;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		
		if (SecurityConfiguration.isLogin()) {
	        return "redirect:/";
	    }
		
		return "login";
	}

}
