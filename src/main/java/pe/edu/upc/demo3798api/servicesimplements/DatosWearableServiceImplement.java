package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.DatosWearable;
import pe.edu.upc.demo3798api.repositories.IDatosWearableRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IDatosWearableService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class DatosWearableServiceImplement implements IDatosWearableService {
    @Autowired private IDatosWearableRepository datosRepo;

    @Override
    public List<DatosWearable> list() {
        return datosRepo.findAll();
    }

    @Override
    public DatosWearable insert(DatosWearable d) {
        return datosRepo.save(d);
    }

    @Override
    public DatosWearable listId(Integer id) {
        return datosRepo.findById(id).orElse(null);
    }

    @Override
    public DatosWearable update(DatosWearable d) {
        if (!datosRepo.existsById(d.getIdDato())) throw new EntityNotFoundException("Dato no existe");
        return datosRepo.save(d);
    }

    @Override
    public void delete(Integer id) {
        datosRepo.deleteById(id);
    }

    @Override
    public List<Map<LocalDate, Double>> promedioFrecuenciaPorDia(Long userId) {
        return datosRepo.promedioFrecuenciaPorDia(userId);
    }

    @Override
    public Integer maxFrecuenciaCardiaca(Long userId) {
        return datosRepo.maxFrecuenciaCardiaca(userId);
    }
}
