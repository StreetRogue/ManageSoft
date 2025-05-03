package co.edu.unicauca.MicroNotification.access;

import co.edu.unicauca.MicroNotification.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}