package pe.edu.upc.demo3798api.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upc.demo3798api.entities.DatosWearable;
import pe.edu.upc.demo3798api.servicesinterfaces.IDatosWearableService;

@Component("datosAuth")
public class DatosWearableAuthorization {

    @Autowired
    private IDatosWearableService datosService;

    /** Comprueba que el dato con id pertenece al username */
    public boolean isOwner(String username, int datoId) {
        DatosWearable d = datosService.listId(datoId);
        return d != null && d.getUsuario().getUsername().equals(username);
    }

    /** Comprueba que el userId coincide con el username */
    public boolean isUser(String username, Long userId) {
        // buscamos cualquiera de los datos: basta con comprobar el propio usuario
        // o podrías hacer repo.exists… pero aquí:
        return datosService.list().stream()
                .anyMatch(d -> d.getUsuario().getUsername().equals(username)
                        && d.getUsuario().getId().equals(userId));
    }
}
