package co.edu.unicauca.companyServices.repositories;



import co.edu.unicauca.companyServices.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}