package pe.edu.upc.demo3798api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.demo3798api.entities.MensajesChat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IMensajesChatRepository extends JpaRepository<MensajesChat, Integer> {

    List<MensajesChat> findByUsuario_Id(Long id);


    List<MensajesChat> findByReunion_IdReunion(Integer idReunion);

    @Query("SELECT m FROM MensajesChat m WHERE m.fechaEnvio BETWEEN :desde AND :hasta")
    List<MensajesChat> buscarPorRangoFechas(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("SELECT m FROM MensajesChat m WHERE LOWER(m.mensaje) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<MensajesChat> buscarPorTexto(@Param("texto") String texto);
}
