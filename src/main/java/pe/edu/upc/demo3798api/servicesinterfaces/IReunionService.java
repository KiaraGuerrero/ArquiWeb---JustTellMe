package pe.edu.upc.demo3798api.servicesinterfaces;

import pe.edu.upc.demo3798api.entities.Reunion;

import java.util.List;

public interface IReunionService {
    List<Reunion> list();
    Reunion insert(Reunion r);
    Reunion listId(Integer id);
    Reunion update(Reunion r);
    void delete(Integer id);
}
