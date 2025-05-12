package pe.edu.upc.demo3798api.servicesinterfaces;

import pe.edu.upc.demo3798api.entities.MensajesChat;

import java.time.LocalDateTime;
import java.util.List;

public interface IMensajesChatService {
    List<MensajesChat> list();
    MensajesChat insert(MensajesChat mensaje);
    MensajesChat listId(Integer id);
    MensajesChat update(MensajesChat mensaje);
    void delete(Integer id);
    MensajesChat crearMensaje(Long idUsuario, Long idReunion, String mensaje, LocalDateTime fechaEnvio);
    List<MensajesChat> listarPorUsuario(Integer idUsuario);
    List<MensajesChat> listarPorReunion(Integer idReunion);
    List<MensajesChat> buscarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);
    List<MensajesChat> buscarPorTexto(String texto);
}
