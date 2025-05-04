package co.edu.unicauca.companyServices.repositories;

import co.edu.unicauca.companyServices.entities.Empresa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {

    // Consultas básicas de existencia
    boolean existsByNitEmpresa(String nitEmpresa);
    boolean existsByEmailEmpresa(String emailEmpresa);
    boolean existsByNombreUsuario(String nombreUsuario);


    @EntityGraph(attributePaths = {"proyectos"})
    @Query("SELECT e FROM Empresa e WHERE e.nombreUsuario = :username AND e.contrasenaUsuario = :password")
    Optional<Empresa> findByUsernameAndPassword(String username, String password);


    @EntityGraph(attributePaths = {"proyectos"})
    @Query("SELECT e FROM Empresa e WHERE e.nombreUsuario = :username ")
    Optional<Empresa> findByUsername(String username);


}