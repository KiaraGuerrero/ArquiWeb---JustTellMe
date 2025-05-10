package pe.edu.upc.demo3798api.servicesinterfaces;

import pe.edu.upc.demo3798api.entities.EvolucionEmocional;

import java.time.LocalDateTime;
import java.util.List;

public interface IEvolucionEmocionalService {
    List<EvolucionEmocional> list();
    EvolucionEmocional insert(EvolucionEmocional evolucion);
    EvolucionEmocional listId(Integer id);
    EvolucionEmocional update(EvolucionEmocional evolucion);
    void delete(Integer id);
    EvolucionEmocional crearEvolucion(Long idPaciente, String estadoEmocional, String observaciones, LocalDateTime fechaRegistro);

}