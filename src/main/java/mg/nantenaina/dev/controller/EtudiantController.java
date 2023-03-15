package mg.nantenaina.dev.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import mg.nantenaina.dev.model.Etudiant;
import mg.nantenaina.dev.repository.EtudiantRepository;

@Controller
public class EtudiantController {

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

	@Autowired
	private EtudiantRepository etudiantRepository;

	@GetMapping("/add-etudiant")
	public String showRegistrationForm(Etudiant etudiant) {
		return "add-etudiant";
	}

	@PostMapping("/add-etudiant")
	public String addEtudiant(@Validated Etudiant etudiant, BindingResult result, Model model,
			@RequestParam("image") MultipartFile file,
			@RequestParam("sexe") String sexe) throws IOException {

		/*if (result.hasErrors()) {
			System.out.println(result);
			return "add-etudiant";
		}*/

		// System.out.println(UPLOAD_DIRECTORY);

		if (file.getOriginalFilename() != "") {

			StringBuilder fileNames = new StringBuilder();
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			Files.write(fileNameAndPath, file.getBytes());

			etudiant.setImage(file.getOriginalFilename());
		}else {
			etudiant.setImage("img_avatar.png");
		}
		
		etudiant.setSexe(sexe);

		etudiantRepository.save(etudiant);

		return "redirect:/";
	}

	@GetMapping("/")
	public String showEtudiantList(Model model) {
		model.addAttribute("etudiants", etudiantRepository.findAll());
		//return "index";
		return "home";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Etudiant etudiant = etudiantRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid etudiant Id:" + id));
		model.addAttribute("etudiant", etudiant);
		return "update-etudiant";
	}

	@PostMapping("/update/{id}")
	public String updateEtudiant(@PathVariable("id") long id, @Validated Etudiant etudiant, BindingResult result,
			Model model, @RequestParam("image") MultipartFile file) throws IOException {

		/*if (result.hasErrors()) {
			etudiant.setId(id);
			return "update-etudiant";
		}*/

		// System.out.println(UPLOAD_DIRECTORY);
		if (file.getOriginalFilename() != "") {

			StringBuilder fileNames = new StringBuilder();
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			Files.write(fileNameAndPath, file.getBytes());

			etudiant.setImage(file.getOriginalFilename());
		}else {
			String image = etudiantRepository.findById(id).get().getImage();
			etudiant.setImage(image);
		}

		etudiantRepository.save(etudiant);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteEtudiant(@PathVariable("id") long id, Model model) {
		Etudiant etudiant = etudiantRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid etudiant Id:" + id));
		etudiantRepository.delete(etudiant);
		return "redirect:/";
	}

}
