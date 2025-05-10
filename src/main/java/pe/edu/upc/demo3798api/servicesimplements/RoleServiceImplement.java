package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.Role;
import pe.edu.upc.demo3798api.repositories.IRoleRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IRoleService;

import java.util.List;

@Service
public class RoleServiceImplement implements IRoleService {
    @Autowired
    private IRoleRepository roleRepo;

    @Override
    public List<Role> list() {
        return roleRepo.findAll();
    }

    @Override
    public Role insert(Role r) {
        return roleRepo.save(r);
    }

    @Override
    public Role listId(Long id) {
        return roleRepo.findById(id).orElse(null);
    }

    @Override
    public Role update(Role r) {
        if (!roleRepo.existsById(r.getId())) throw new EntityNotFoundException("Rol no existe");
        return roleRepo.save(r);
    }

    @Override
    public void delete(Long id) {
        roleRepo.deleteById(id);
    }
}
