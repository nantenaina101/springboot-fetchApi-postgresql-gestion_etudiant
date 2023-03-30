package mg.nantenaina.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.nantenaina.dev.config.SecurityConfiguration;
import mg.nantenaina.dev.dto.UserRegistrationDto;
import mg.nantenaina.dev.model.Profil;
import mg.nantenaina.dev.repository.ProfilRepository;
import mg.nantenaina.dev.repository.UserRepository;
import mg.nantenaina.dev.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfilRepository profilRepository;
	
	private UserService userService;

	public RegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm() {
		if (SecurityConfiguration.isLogin()) {
	        return "redirect:/";
	    }
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(Model model,@ModelAttribute("user") UserRegistrationDto registrationDto, @RequestParam("password") String password, @RequestParam("repassword") String repassword) {
		String email = registrationDto.getEmail();
		if(!password.equals(repassword)) {
			model.addAttribute("notmatch", "Mots de passe non identiques");
			return "registration";
		}else if(userRepository.findByEmail(email) != null) {
			model.addAttribute("mailexist", "Adresse email déjà utilisé");
			return "registration";
		}
		
		Profil profil = new Profil(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail());
		profilRepository.save(profil);
		userService.save(registrationDto);
		return "redirect:/";
	}
}
