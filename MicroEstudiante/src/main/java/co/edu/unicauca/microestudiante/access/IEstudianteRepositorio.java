package co.edu.unicauca.microestudiante.access;

import co.edu.unicauca.microestudiante.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEstudianteRepositorio extends JpaRepository<Estudiante, Long> {
    @Query("SELECT e FROM Estudiante e WHERE e.codigoSimcaEstudiante = :codigoSimca")
    boolean existsSimca(Long codigoSimca);

    @Query("SELECT e FROM Estudiante e WHERE e.emailEstudiante = :email")
    boolean existsEmail(String email);

    @Query("SELECT e FROM Estudiante e WHERE e.nombreUsuario = :usuario")
    boolean existsUser(String usuario);

    @Query("SELECT e FROM Estudiante e WHERE e.nombreUsuario = :nombreUsuario AND e.contrasenaUsuario = :contrasena")
    Optional<Estudiante> findByUsernameAndPassword(String nombreUsuario, String contrasena);

    @Query("SELECT COUNT(e) FROM Estudiante e")
    Integer contarEstudiantes();
}
