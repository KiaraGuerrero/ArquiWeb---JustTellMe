package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.Reunion;
import pe.edu.upc.demo3798api.repositories.IReunionRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IReunionService;

import java.util.List;

@Service
public class ReunionServiceImplement implements IReunionService {

    @Autowired
    private IReunionRepository reunionRepo;

    @Override
    public List<Reunion> list() {
        return reunionRepo.findAll();
    }

    @Override
    public Reunion insert(Reunion r) {
        return reunionRepo.save(r);
    }

    @Override
    public Reunion listId(Integer id) {
        return reunionRepo.findById(id).orElse(null);
    }

    @Override
    public Reunion update(Reunion r) {
        // opcional: validar que exista antes
        if (!reunionRepo.existsById(r.getIdReunion())) {
            throw new EntityNotFoundException("Reunión no existe");
        }
        return reunionRepo.save(r);
    }

    @Override
    public void delete(Integer id) {
        if (!reunionRepo.existsById(id)) {
            throw new EntityNotFoundException("Reunión no existe");
        }
        reunionRepo.deleteById(id);
    }

}