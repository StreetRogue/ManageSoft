package co.edu.unicauca.microcoordinador.access.repositories;

import co.edu.unicauca.microcoordinador.entities.Coordinador;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICoordinadorRepositorio extends JpaRepository<Coordinador, Long> {

    // Buscar coordinador por nombre
    //Optional<Coordinador> findByNombreCoordinador(String nombreUsuario);
    @Query("SELECT e FROM Coordinador e WHERE e.nombreUsuario = :nombreUsuario AND e.contrasenaUsuario = :contrasenaUsuario")
    Optional<Coordinador> findByNombreCoordinadorAndContrasenaUsuario(String nombreUsuario, String contrasenaUsuario);


    // Nuevo método para buscar coordinador por email
    Optional<Coordinador> findByEmailCoordinador(String emailCoordinador);

    // Nuevo método para verificar si existe un coordinador con un email dado
    boolean existsByEmailCoordinador(String emailCoordinador);
}
