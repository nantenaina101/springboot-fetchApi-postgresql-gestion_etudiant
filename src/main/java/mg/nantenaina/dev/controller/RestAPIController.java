package mg.nantenaina.dev.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mg.nantenaina.dev.config.SecurityConfiguration;
import mg.nantenaina.dev.model.Etudiant;
import mg.nantenaina.dev.model.Profil;
import mg.nantenaina.dev.model.User;
import mg.nantenaina.dev.repository.EtudiantRepository;
import mg.nantenaina.dev.repository.ProfilRepository;
import mg.nantenaina.dev.repository.UserRepository;

@RestController
public class RestAPIController {
	
	@Autowired
	private BCryptPasswordEncoder imageNameEncoder;
	
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ProfilRepository profilRepository;
	
	@Autowired
	private EtudiantRepository etudiantRepository;

	@GetMapping("/api/v1/etudiants")
	public List<Etudiant> retrieveAllStudents() {
		return etudiantRepository.findAll();
	}

	@GetMapping("/api/v1/etudiant/{id}")
	public Etudiant retrieveStudent(@PathVariable long id) throws Exception {
		Optional<Etudiant> student = etudiantRepository.findById(id);

		if (student.isEmpty())
			throw new Exception("Acun étudiant trouvé pour l'identifiant " + id);

		return student.get();
	}

	@DeleteMapping("/api/v1/etudiant/{id}")
	public String deleteStudent(@PathVariable long id) {

		Optional<Etudiant> studentOptional = etudiantRepository.findById(id);

		String text = "";

		if (studentOptional.isEmpty()) {
			text = "Aucun étudiant trouvé pour l'identifiant " + id;
		} else {
			etudiantRepository.deleteById(id);
			text = "Etudiant supprimé avec succès";
		}

		return text;

	}

	/*
	 * @PostMapping("/api/v1/add-etudiant") public ResponseEntity<Object>
	 * createStudent(@RequestBody Etudiant student) { Etudiant savedStudent =
	 * etudiantRepository.save(student);
	 * 
	 * URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	 * .buildAndExpand(savedStudent.getId()).toUri();
	 * 
	 * return ResponseEntity.created(location).build();
	 * 
	 * }
	 */

	
	  /*@PostMapping("/api/v1/add-etudiant") public String createStudent(@RequestBody
	  Etudiant student) {
	  
		  etudiantRepository.save(student);
		  
		  return "Etudiant enregistré avec succès";
	  
	  }*/
	 

	@PostMapping("/api/v1/add-etudiant")
	public String createStudent(@RequestBody MultipartFile file,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email,
			@RequestParam("telephone") String telephone,
			@RequestParam("sexe") String sexe,
			@RequestParam("classe") String classe) throws IOException {

		//JSONObject json = new JSONObject(object);


		System.out.println("classe : " + classe);

		System.out.println("sexe : " + sexe);


		System.out.println("firstname : " + firstname);


		System.out.println("lastname : " + lastname);

		System.out.println("email : " + email);

		System.out.println("telephone : " + telephone);
		
		Etudiant etudiant = new Etudiant();
		
		if (file != null) {

			System.out.println("image : " + file.getOriginalFilename());
			
			//Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			String ext = getFileExtension(file.getOriginalFilename());
			
			System.out.println("Extension : " + ext);
			
			String newPhotoName = imageNameEncoder.encode(file.getOriginalFilename()).replaceAll("[^A-Za-z0-9]", "_")+ext;
			
			System.out.println("image : " + newPhotoName);
			
			//Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			
			//Files.write(fileNameAndPath, file.getBytes());
			
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, newPhotoName);
			
			Files.write(fileNameAndPath, file.getBytes());
			
			etudiant.setImage(newPhotoName);
		
		}else {
			etudiant.setImage("img_avatar.png");
			System.out.println("image : img_avatar.png");
		}
		
		
		etudiant.setClasse(Integer.parseInt(classe));
		
		etudiant.setSexe(sexe);
		
		etudiant.setFirstname(firstname);
		
		etudiant.setLastname(lastname);
		
		etudiant.setEmail(email);
		
		etudiant.setTelephone(telephone);
		
		etudiantRepository.save(etudiant);
		
		return "Etudiant enregistré avec succès";

	}

	/*
	 * @PutMapping("/api/v1/update-etudiant/{id}") public ResponseEntity<Object>
	 * updateStudent(@RequestBody Etudiant student, @PathVariable long id) {
	 * 
	 * Optional<Etudiant> studentOptional = etudiantRepository.findById(id);
	 * 
	 * if (studentOptional.isEmpty()) return ResponseEntity.notFound().build();
	 * 
	 * student.setId(id);
	 * 
	 * etudiantRepository.save(student);
	 * 
	 * return ResponseEntity.noContent().build(); }
	 */

	/*@PutMapping("/api/v1/update-etudiant/{id}")
	public String updateStudent(@RequestBody Etudiant student, @PathVariable long id) {

		Optional<Etudiant> studentOptional = etudiantRepository.findById(id);

		String text = "";
		if (studentOptional.isEmpty()) {
			text = "Aucun étudiant trouvé pour l'identifiant " + id;
		} else {
			student.setId(id);
			etudiantRepository.save(student);
			text = "Etudiant modifié avec succès";
		}

		return text;

	}*/
	
	@PutMapping("/api/v1/update-etudiant/{id}")
	public String updateStudent(@RequestBody MultipartFile file,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email,
			@RequestParam("telephone") String telephone,
			@RequestParam("sexe") String sexe,
			@RequestParam("classe") String classe, @PathVariable long id) throws IOException {

		Optional<Etudiant> studentOptional = etudiantRepository.findById(id);

		String text = "";
		if (studentOptional.isEmpty()) {
			text = "Aucun étudiant trouvé pour l'identifiant " + id;
		} else {
			
			Etudiant etudiant = new Etudiant();
			
			etudiant.setId(id);
			
			System.out.println("classe : " + classe);

			System.out.println("sexe : " + sexe);

			System.out.println("firstname : " + firstname);

			System.out.println("lastname : " + lastname);

			System.out.println("email : " + email);

			System.out.println("telephone : " + telephone);

			if (file != null) {

				System.out.println("image : " + file.getOriginalFilename());
				
				String ext = getFileExtension(file.getOriginalFilename());
				
				System.out.println("Extension : " + ext);
				
				String newPhotoName = imageNameEncoder.encode(file.getOriginalFilename()).replaceAll("[^A-Za-z0-9]", "_")+ext;
				
				System.out.println("image : " + newPhotoName);
				
				//Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
				
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, newPhotoName);
				
				Files.write(fileNameAndPath, file.getBytes());
				
				etudiant.setImage(newPhotoName);
			
			}else {
				String image = etudiantRepository.findById(id).get().getImage();
				etudiant.setImage(image);
				System.out.println("image : " + image);
			}
			
			etudiant.setClasse(Integer.parseInt(classe));
			
			etudiant.setSexe(sexe);
			
			etudiant.setFirstname(firstname);
			
			etudiant.setLastname(lastname);
			
			etudiant.setEmail(email);
			
			etudiant.setTelephone(telephone);
			
			etudiantRepository.save(etudiant);

			text = "Etudiant modifié avec succès";
			
		}

		return text;

	}
	
	@GetMapping("/user/{id}")
	public User getUserProfil(@PathVariable long id) throws Exception {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new Exception("Acun utilisateur trouvé pour l'identifiant " + id);

		return user.get();
	}
	
	public String getFileExtension(String name) {
	    int lastIndex = name.lastIndexOf(".");
	    if (lastIndex == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndex);
	}
	
	@PutMapping("/api/v1/update-profil-picture")
	public String updateProfil(@RequestParam MultipartFile file) throws IOException {
		String email = SecurityConfiguration.getUserEmail();
		long id = profilRepository.findByEmail(email).getId();
		Optional<Profil> profilOptional = profilRepository.findById(id);

		String text = "";
		if (profilOptional.isEmpty()) {
			text = "Aucun profil trouvé pour l'identifiant " + id;
		} else {
			
			Profil profil = new Profil();
			
			profil.setId(id);

			String ext = getFileExtension(file.getOriginalFilename());

			String newPhotoName = imageNameEncoder.encode(file.getOriginalFilename()).replaceAll("[^A-Za-z0-9]", "_")+ext;
			
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, newPhotoName);
			
			Files.write(fileNameAndPath, file.getBytes());
			
			profil.setProfil(newPhotoName);
			
			profil.setCouverture(profilRepository.findByEmail(email).getCouverture());
			
			profil.setFirstname(profilRepository.findByEmail(email).getFirstname());
			
			profil.setLastname(profilRepository.findByEmail(email).getLastname());
			
			profil.setEmail(email);
			
			profilRepository.save(profil);

			text = "Photo de profil modifié avec succès";
			
		}

		return text;

	}
	
	@PutMapping("/api/v1/update-couverture-picture")
	public String updateCouverture(@RequestParam MultipartFile file) throws IOException {
		String email = SecurityConfiguration.getUserEmail();
		long id = profilRepository.findByEmail(email).getId();
		Optional<Profil> profilOptional = profilRepository.findById(id);

		String text = "";
		if (profilOptional.isEmpty()) {
			text = "Aucun profil trouvé pour l'identifiant " + id;
		} else {
			
			Profil profil = new Profil();
			
			profil.setId(id);

			String ext = getFileExtension(file.getOriginalFilename());

			String newPhotoName = imageNameEncoder.encode(file.getOriginalFilename()).replaceAll("[^A-Za-z0-9]", "_")+ext;
			
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, newPhotoName);
			
			Files.write(fileNameAndPath, file.getBytes());
			
			profil.setCouverture(newPhotoName);
			
			profil.setProfil(profilRepository.findByEmail(email).getProfil());
			
			profil.setFirstname(profilRepository.findByEmail(email).getFirstname());
			
			profil.setLastname(profilRepository.findByEmail(email).getLastname());
			
			profil.setEmail(email);
			
			profilRepository.save(profil);

			text = "Photo de couverture modifié avec succès";
			
		}

		return text;

	}

}
