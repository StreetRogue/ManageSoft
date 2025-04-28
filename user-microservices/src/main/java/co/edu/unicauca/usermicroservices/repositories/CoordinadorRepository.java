package co.edu.unicauca.usermicroservices.repositories;


import co.edu.unicauca.usermicroservices.entities.Coordinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinadorRepository extends JpaRepository<Coordinador, Long> {
}
