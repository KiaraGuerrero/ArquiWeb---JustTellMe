package pe.edu.upc.demo3798api.servicesinterfaces;

import pe.edu.upc.demo3798api.entities.Role;

import java.util.List;

public interface IRoleService {
    List<Role> list();
    Role insert(Role r);
    Role listId(Long id);
    Role update(Role r);
    void delete(Long id);
}
