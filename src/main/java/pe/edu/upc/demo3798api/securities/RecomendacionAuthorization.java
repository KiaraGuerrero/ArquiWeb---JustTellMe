package pe.edu.upc.demo3798api.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upc.demo3798api.entities.Recomendacion;
import pe.edu.upc.demo3798api.servicesinterfaces.IRecomendacionService;

@Component("recomendacionAuth")
public class RecomendacionAuthorization {

    @Autowired
    private IRecomendacionService recService;

    /** Devuelve true si el username es dueño de la recomendación */
    public boolean isOwner(String username, Integer recId) {
        Recomendacion r = recService.listId(recId);
        return r != null && r.getUsuario().getUsername().equals(username);
    }

    /** (opcional) para chequear en listAll si hay al menos una recomendación del usuario */
    public boolean userHasAny(String username) {
        return recService.list().stream()
                .anyMatch(r -> r.getUsuario().getUsername().equals(username));
    }
}
