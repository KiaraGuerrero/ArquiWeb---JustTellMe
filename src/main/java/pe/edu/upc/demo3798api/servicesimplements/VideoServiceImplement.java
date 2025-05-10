package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.Video;
import pe.edu.upc.demo3798api.repositories.IVideoRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IVideoService;

import java.util.List;

@Service
public class VideoServiceImplement implements IVideoService {
    @Autowired private IVideoRepository videoRepo;

    @Override
    public List<Video> list() {
        return videoRepo.findAll();
    }

    @Override
    public Video insert(Video v) {
        return videoRepo.save(v);
    }

    @Override
    public Video listId(Integer id) {
        return videoRepo.findById(id).orElse(null);
    }

    @Override
    public Video update(Video v) {
        if (!videoRepo.existsById(v.getIdVideo())) throw new EntityNotFoundException("Video no existe");
        return videoRepo.save(v);
    }

    @Override
    public void delete(Integer id) {
        videoRepo.deleteById(id);
    }
}
