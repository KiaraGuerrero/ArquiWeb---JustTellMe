package pe.edu.upc.demo3798api.servicesimplements;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.demo3798api.entities.MensajesChat;
import pe.edu.upc.demo3798api.entities.Reunion;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.repositories.IMensajesChatRepository;
import pe.edu.upc.demo3798api.repositories.IReunionRepository;
import pe.edu.upc.demo3798api.repositories.IUserRepository;
import pe.edu.upc.demo3798api.servicesinterfaces.IMensajesChatService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajesChatServiceImplement implements IMensajesChatService {

    @Autowired
    private IMensajesChatRepository mensajesChatRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IReunionRepository reunionRepository;

    @Override
    public List<MensajesChat> list() {
        return mensajesChatRepository.findAll();
    }

    @Override
    public MensajesChat insert(MensajesChat mensaje) {
        return mensajesChatRepository.save(mensaje);
    }

    @Override
    public MensajesChat listId(Integer id) {
        return mensajesChatRepository.findById(id).orElse(null);
    }

    @Override
    public MensajesChat update(MensajesChat mensaje) {
        if (!mensajesChatRepository.existsById(mensaje.getIdMensaje()))
            throw new EntityNotFoundException("Mensaje no existe");
        return mensajesChatRepository.save(mensaje);
    }

    @Override
    public void delete(Integer id) {
        mensajesChatRepository.deleteById(id);
    }

    @Override
    public MensajesChat crearMensaje(Long idUsuario, Long idReunion, String mensajeTexto, LocalDateTime fechaEnvio) {
        Users usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no existe"));
        Reunion reunion = reunionRepository.findById(idReunion.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Reunión no existe"));

        MensajesChat mensaje = new MensajesChat();
        mensaje.setUsuario(usuario);
        mensaje.setReunion(reunion);
        mensaje.setMensaje(mensajeTexto);
        mensaje.setFechaEnvio(fechaEnvio);

        return mensajesChatRepository.save(mensaje);
    }
    @Override
    public List<MensajesChat> listarPorUsuario(Integer idUsuario) {
        return mensajesChatRepository.findByUsuario_Id(idUsuario.longValue());

    }

    @Override
    public List<MensajesChat> listarPorReunion(Integer idReunion) {
        return mensajesChatRepository.findByReunion_IdReunion(idReunion);
    }

    @Override
    public List<MensajesChat> buscarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return mensajesChatRepository.buscarPorRangoFechas(desde, hasta);
    }

    @Override
    public List<MensajesChat> buscarPorTexto(String texto) {
        return mensajesChatRepository.buscarPorTexto(texto);
    }

}
