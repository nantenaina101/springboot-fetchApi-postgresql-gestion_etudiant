package mg.nantenaina.dev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profil")
public class Profil {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    @Column(name = "firstname", nullable = true)
    private String firstname;
    
    @Column(name = "lastname", nullable = true)
    private String lastname;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "sexe", nullable = true)
    private String sexe;
    
    @Column(name = "telephone", nullable = true)
    private String telephone;
    
    @Column(name = "profil", nullable = true)
    private String profil;
    
    @Column(name = "couverture", nullable = true)
    private String couverture;

    @Column(name = "adresse", nullable = true)
    private String adresse;
    
    @Column(name = "quartier", nullable = true)
    private String quartier;
    
    @Column(name = "commune", nullable = true)
    private String commune;
    
    @Column(name = "ville", nullable = true)
    private String ville;
    
    @Column(name = "pays", nullable = true)
    private String pays;
    
    @Column(name = "website", nullable = true)
    private String website;
    
    @Column(name = "github", nullable = true)
    private String github;
    
    @Column(name = "twitter", nullable = true)
    private String twitter;
    
    @Column(name = "instagram", nullable = true)
    private String instagram;
    
    @Column(name = "facebook", nullable = true)
    private String facebook;
    
    @Column(name = "tiktok", nullable = true)
    private String tiktok;
    
    @Column(name = "profession", nullable = true)
    private String profession;
    
    public Profil() {
    	
    }
    
    public Profil(String firstname, String lastname, String email) {
    	this.firstname = firstname;
    	this.lastname = lastname;
    	this.email = email;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public String getCouverture() {
		return couverture;
	}

	public void setCouverture(String couverture) {
		this.couverture = couverture;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTiktok() {
		return tiktok;
	}

	public void setTiktok(String tiktok) {
		this.tiktok = tiktok;
	}

}
