package pe.edu.upc.demo3798api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.demo3798api.entities.Recomendacion;

@Repository
public interface IRecomendacionRepository extends JpaRepository<Recomendacion, Integer> {
    // Ejemplo: List<Recomendacion> findByUsuario_Id(Long idUsuario);
    // Ejemplo: List<Recomendacion> findByEstado(String estado);
}
