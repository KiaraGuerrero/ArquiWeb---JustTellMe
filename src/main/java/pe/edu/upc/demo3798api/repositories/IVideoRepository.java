package pe.edu.upc.demo3798api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.demo3798api.entities.Video;

@Repository
public interface IVideoRepository extends JpaRepository<Video, Integer> {
    // Ejemplo: List<Video> findByCategoria(String categoria);
}
