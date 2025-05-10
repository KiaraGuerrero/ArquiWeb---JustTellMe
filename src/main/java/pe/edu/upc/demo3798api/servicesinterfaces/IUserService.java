package pe.edu.upc.demo3798api.servicesinterfaces;

import pe.edu.upc.demo3798api.entities.Users;

import java.util.List;

public interface IUserService {
    List<Users> list();
    Users insert(Users u);
    Users listId(Long id);
    Users update(Users u);
    void delete(Long id);

}
