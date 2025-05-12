package pe.edu.upc.demo3798api.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upc.demo3798api.entities.MensajesChat;
import pe.edu.upc.demo3798api.servicesinterfaces.IMensajesChatService;

@Component("mensajeAuth")
public class MensajeChatAuthorization {

    @Autowired
    private IMensajesChatService servicio;

    /** Comprueba si el username es autor del mensaje */
    public boolean isAuthor(String username, int mensajeId) {
        MensajesChat m = servicio.listId(mensajeId);
        return m != null && m.getUsuario().getUsername().equals(username);
    }

    /** Recupera el username dueño de un mensaje por ID */
    public String usernameById(int mensajeId) {
        MensajesChat m = servicio.listId(mensajeId);
        return (m != null) ? m.getUsuario().getUsername() : "";
    }
}
