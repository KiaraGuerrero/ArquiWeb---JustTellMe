package pe.edu.upc.demo3798api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.demo3798api.entities.Reunion;

@Repository
public interface IReunionRepository extends JpaRepository<Reunion, Integer> {
    // Ejemplo: List<Reunion> findByUsuario_Id(Long idUsuario);
    // Ejemplo: List<Reunion> findByTipo(String tipo);
}
