package mg.nantenaina.dev.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mg.nantenaina.dev.config.SecurityConfiguration;
import mg.nantenaina.dev.model.Etudiant;
import mg.nantenaina.dev.model.Profil;
import mg.nantenaina.dev.repository.ProfilRepository;

@Controller
public class ProfilController{
	
	@Autowired
	ProfilRepository profilRepository;
	
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
	
	@GetMapping("/user/profil")
	public String showUserProfil(Model model) {
		String email = SecurityConfiguration.getUserEmail();
		model.addAttribute("profil", profilRepository.findByEmail(email));
		return "profil";
	}
	
	@GetMapping("/user/profil/edit")
	public String editUserProfil(Model model) {
		String email = SecurityConfiguration.getUserEmail();
		model.addAttribute("profil", profilRepository.findByEmail(email));
		return "edit-profil";
	}
	
	@PostMapping("/update/profil/{id}")
	public String updateCouverture(@PathVariable("id") long id, @Validated Profil profil) {
		
		String email = SecurityConfiguration.getUserEmail();
		
		Optional<Profil> profilOptional = profilRepository.findById(id);

		String text = "";
		if (profilOptional.isEmpty()) {
			text = "Aucun profil trouvé pour l'identifiant " + id;
		} else {

			//profil.setId(id);
			
			profil.setEmail(email);
			
			profil.setCouverture(profilRepository.findByEmail(email).getCouverture());
			
			profil.setProfil(profilRepository.findByEmail(email).getProfil());
			
			profilRepository.save(profil);

			text = "Photo de couverture modifié avec succès";
			
		}

		return "redirect:/user/profil";

	}
	
}
