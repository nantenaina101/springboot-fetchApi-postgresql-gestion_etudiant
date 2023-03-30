package mg.nantenaina.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.nantenaina.dev.model.Profil;

@Repository
public interface ProfilRepository extends JpaRepository <Profil, Long>{
	Profil findByEmail(String email);
}
