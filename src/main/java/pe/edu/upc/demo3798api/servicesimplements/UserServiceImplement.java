package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.repositories.IUserRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IUserService;

import java.util.List;

@Service
public class UserServiceImplement implements IUserService {
    @Autowired private IUserRepository userRepo;

    @Override
    public List<Users> list() {
        return userRepo.findAll();
    }

    @Override
    public Users insert(Users u) {
        return userRepo.save(u);
    }

    @Override
    public Users listId(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public Users update(Users u) {
        if (!userRepo.existsById(u.getId())) throw new EntityNotFoundException("Usuario no existe");
        return userRepo.save(u);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.buscarUsername(username) > 0;
    }
}

