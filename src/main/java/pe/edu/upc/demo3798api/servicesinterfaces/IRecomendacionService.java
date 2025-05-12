package pe.edu.upc.demo3798api.servicesinterfaces;

import pe.edu.upc.demo3798api.entities.Recomendacion;


import java.util.List;

public interface IRecomendacionService {
    List<Recomendacion> list();
    Recomendacion insert(Recomendacion r);
    Recomendacion listId(Integer id);
    Recomendacion update(Recomendacion r);
    void delete(Integer id);
}
