package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.MensajesChatDTO;
import pe.edu.upc.demo3798api.entities.MensajesChat;
import pe.edu.upc.demo3798api.servicesinterfaces.IMensajesChatService;
import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajesChatController {

    @Autowired
    private IMensajesChatService mensajesChatService;

    @GetMapping
    public List<MensajesChat> all() {
        return mensajesChatService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajesChat> one(@PathVariable int id) {
        MensajesChat m = mensajesChatService.listId(id);
        if (m == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(m);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        mensajesChatService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
