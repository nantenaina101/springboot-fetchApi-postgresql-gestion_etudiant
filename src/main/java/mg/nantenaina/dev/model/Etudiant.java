package mg.nantenaina.dev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Etudiant {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="etudiant_generator")
	@SequenceGenerator(name="etudiant_generator", allocationSize=1, sequenceName="etudiant_generator")
    @Column(name="id")
    private long id;
    
    @Column(name = "firstname")
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;
    
    @Column(name = "lastname")
    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;
    
    @Column(name = "classe")
    @NotBlank(message = "La classe est obligatoire")
    private int classe;
    
    @Column(name = "sexe")
    @NotBlank(message = "Le sexe est obligatoire")
    private String sexe;
    
    @Column(name = "email")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    @Column(name = "telephone")
    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;
    
    @Column(name = "image")
    private String image;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
