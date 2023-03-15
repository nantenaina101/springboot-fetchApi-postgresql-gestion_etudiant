package mg.nantenaina.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mg.nantenaina.dev.model.Etudiant;

@Repository
public interface EtudiantRepository extends JpaRepository <Etudiant, Long>{
	
}
