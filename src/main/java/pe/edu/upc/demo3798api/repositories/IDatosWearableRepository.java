package pe.edu.upc.demo3798api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.demo3798api.entities.DatosWearable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface IDatosWearableRepository extends JpaRepository<DatosWearable, Integer> {
    @Query("""
    SELECT new map(
       FUNCTION('date', d.fechaRegistro) AS dia,
       AVG(d.frecuenciaCardiaca) AS promedio
    )
    FROM DatosWearable d
    WHERE d.usuario.id = :userId
    GROUP BY FUNCTION('date', d.fechaRegistro)
  """)
    List<Map<LocalDate,Double>> promedioFrecuenciaPorDia(@Param("userId") Long userId);


    @Query("SELECT MAX(d.frecuenciaCardiaca) FROM DatosWearable d WHERE d.usuario.id = :userId")
    Integer maxFrecuenciaCardiaca(@Param("userId") Long userId);
}
