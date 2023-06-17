package ma.enset.hopital.repository;

import ma.enset.hopital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    //si une methode return une page on uajoute parametre pageabla
    Page<Patient> findByNomContains(String keyword, Pageable pageable);
    //autre facon de chercher
    @Query("select p from Patient p where p.nom like :x")
    Page<Patient> chercher(@Param("x")String keyword, Pageable pageable);

}
