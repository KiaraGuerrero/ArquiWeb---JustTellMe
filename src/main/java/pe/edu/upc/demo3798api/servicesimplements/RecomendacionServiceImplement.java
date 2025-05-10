package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.Recomendacion;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.entities.Video;
import pe.edu.upc.demo3798api.repositories.IRecomendacionRepository;
import pe.edu.upc.demo3798api.repositories.IUserRepository;
import pe.edu.upc.demo3798api.repositories.IVideoRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IRecomendacionService;

import java.util.List;

@Service
public class RecomendacionServiceImplement implements IRecomendacionService {

    @Autowired
    private IRecomendacionRepository recRepo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IVideoRepository videoRepo;

    @Override
    public List<Recomendacion> list() {
        return recRepo.findAll();
    }

    @Override
    public Recomendacion insert(Recomendacion r) {
        return recRepo.save(r);
    }

    @Override
    public Recomendacion listId(Integer id) {
        return recRepo.findById(id).orElse(null);
    }

    @Override
    public Recomendacion update(Recomendacion r) {
        Integer id = r.getIdRecomendacion();
        Recomendacion existing = recRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recomendacion no existe"));
        // actualizar campos
        existing.setEstado(r.getEstado());
        existing.setFechaRecomendacion(r.getFechaRecomendacion());

        // si deseas validar usuario y video existen:
        Users u = userRepo.findById(r.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no existe"));
        Video v = videoRepo.findById(r.getVideo().getIdVideo())
                .orElseThrow(() -> new EntityNotFoundException("Video no existe"));
        existing.setUsuario(u);
        existing.setVideo(v);

        return recRepo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        Recomendacion existing = recRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recomendacion no existe"));
        recRepo.delete(existing);
    }
}
