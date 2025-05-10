package pe.edu.upc.demo3798api.servicesinterfaces;
import pe.edu.upc.demo3798api.entities.DatosWearable;
import java.util.List;


public interface IDatosWearableService {
    List<DatosWearable> list();
    DatosWearable insert(DatosWearable d);
    DatosWearable listId(Integer id);
    DatosWearable update(DatosWearable d);
    void delete(Integer id);
}
