package pe.edu.upc.demo3798api.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.demo3798api.entities.EvolucionEmocional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEvolucionEmocionalRepository extends JpaRepository<EvolucionEmocional, Integer> {

    // Todas las evoluciones de un paciente
    List<EvolucionEmocional> findByPaciente_Id(Long id);

    // Evoluciones desde una fecha
    @Query("SELECT e FROM EvolucionEmocional e WHERE e.fechaRegistro >= :fecha")
    List<EvolucionEmocional> buscarDesdeFecha(@Param("fecha") LocalDateTime fecha);

    // Contar evoluciones de un paciente
    @Query("SELECT COUNT(e) FROM EvolucionEmocional e WHERE e.paciente.id = :id")
    int contarPorPaciente(@Param("id") Long id);

    // Última evolución registrada (con Pageable)
    @Query("SELECT e FROM EvolucionEmocional e WHERE e.paciente.id = :id ORDER BY e.fechaRegistro DESC")
    List<EvolucionEmocional> ultimaEvolucion(@Param("id") Long id, Pageable pageable);
}
