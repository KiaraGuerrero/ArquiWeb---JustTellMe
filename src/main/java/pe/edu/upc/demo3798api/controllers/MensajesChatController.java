package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.MensajesChatDTO;
import pe.edu.upc.demo3798api.entities.MensajesChat;
import pe.edu.upc.demo3798api.servicesinterfaces.IMensajesChatService;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajesChatController {

    @Autowired
    private IMensajesChatService mensajesChatService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<MensajesChat> all() {
        return mensajesChatService.list();
    }

    /** ADMIN, autor o participantes pueden ver un mensaje */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MensajesChat> one(@PathVariable int id) {
        MensajesChat m = mensajesChatService.listId(id);
        if (m == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(m);
    }

    /** PACIENTE y PSICOLOGO pueden enviar mensajes en sus reuniones */
    @PreAuthorize("hasAnyRole('PACIENTE','PSICOLOGO')")
    @PostMapping
    public ResponseEntity<MensajesChat> create(@RequestBody MensajesChatDTO dto) {
        MensajesChat m = mensajesChatService.crearMensaje(dto.idUsuario, dto.idReunion, dto.mensaje, dto.fechaEnvio);
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MensajesChat> update(@PathVariable int id, @RequestBody MensajesChatDTO dto) {
        MensajesChat existente = mensajesChatService.listId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        MensajesChat actualizado = mensajesChatService.crearMensaje(dto.idUsuario, dto.idReunion, dto.mensaje, dto.fechaEnvio);
        actualizado.setIdMensaje(id);
        return ResponseEntity.ok(mensajesChatService.update(actualizado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        mensajesChatService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Listar por usuario (solo ADMIN o el propio usuario) */
    @PreAuthorize("hasAnyRole('ADMIN','PSICOLOGO')")
    @GetMapping("/usuario/{id}")
    public List<MensajesChat> porUsuario(@PathVariable Integer id) {
        return mensajesChatService.listarPorUsuario(id);
    }

    /** ADMIN, PACIENTE o PSICOLOGO pueden ver mensajes de una reunión si participan */
    @PreAuthorize("hasAnyRole('PACIENTE','PSICOLOGO','ADMIN')")
    @GetMapping("/reunion/{id}")
    public List<MensajesChat> porReunion(@PathVariable Integer id) {
        return mensajesChatService.listarPorReunion(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/fechas")
    public List<MensajesChat> porFechas(@RequestParam("desde") String desde, @RequestParam("hasta") String hasta) {
        LocalDateTime f1 = LocalDateTime.parse(desde);
        LocalDateTime f2 = LocalDateTime.parse(hasta);
        return mensajesChatService.buscarPorRangoFechas(f1, f2);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar")
    public List<MensajesChat> porTexto(@RequestParam("q") String texto) {
        return mensajesChatService.buscarPorTexto(texto);
    }

}
