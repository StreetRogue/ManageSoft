package co.edu.unicauca.microcoordinador.access.repositories;

import co.edu.unicauca.microcoordinador.entities.Coordinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoordinadorRepositorio extends JpaRepository<Coordinador, Long> {
    Coordinador findByCodigoSimca(String codigoSimca);
    Coordinador findByEmail(String email);
}