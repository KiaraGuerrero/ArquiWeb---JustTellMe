package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.EvolucionEmocional;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.repositories.IEvolucionEmocionalRepository;
import pe.edu.upc.demo3798api.repositories.IUserRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IEvolucionEmocionalService;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvolucionEmocionalServiceImplement implements IEvolucionEmocionalService {

    @Autowired
    private IEvolucionEmocionalRepository evolucionRepo;

    @Autowired
    private IUserRepository userRepo;

    @Override
    public List<EvolucionEmocional> list() {
        return evolucionRepo.findAll();
    }

    @Override
    public EvolucionEmocional insert(EvolucionEmocional evolucion) {
        return evolucionRepo.save(evolucion);
    }

    @Override
    public EvolucionEmocional listId(Integer id) {
        return evolucionRepo.findById(id).orElse(null);
    }

    @Override
    public EvolucionEmocional update(EvolucionEmocional evolucion) {
        if (!evolucionRepo.existsById(evolucion.getIdEvolucion()))
            throw new EntityNotFoundException("Registro de evolución no existe");
        return evolucionRepo.save(evolucion);
    }

    @Override
    public void delete(Integer id) {
        evolucionRepo.deleteById(id);
    }

    @Override
    public EvolucionEmocional crearEvolucion(Long idPaciente, String estadoEmocional, String observaciones, LocalDateTime fechaRegistro) {
        Users paciente = userRepo.findById(idPaciente)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no existe"));

        EvolucionEmocional evolucion = new EvolucionEmocional();
        evolucion.setPaciente(paciente);
        evolucion.setEstadoEmocional(estadoEmocional);
        evolucion.setObservaciones(observaciones);
        evolucion.setFechaRegistro(fechaRegistro);

        return evolucionRepo.save(evolucion);
    }

    @Override
    public List<EvolucionEmocional> listarPorPaciente(Integer idUsuario) {
        return evolucionRepo.findByPaciente_Id(idUsuario.longValue());
    }


    @Override
    public List<EvolucionEmocional> listarDesdeFecha(LocalDateTime fecha) {
        return evolucionRepo.buscarDesdeFecha(fecha);
    }

    @Override
    public int contarPorPaciente(Long idPaciente) {
        return evolucionRepo.contarPorPaciente(idPaciente);
    }

    @Override
    public EvolucionEmocional obtenerUltimaEvolucion(Long idPaciente) {
        List<EvolucionEmocional> lista = evolucionRepo.ultimaEvolucion(idPaciente, PageRequest.of(0, 1));
        return lista.isEmpty() ? null : lista.get(0);
    }

}
