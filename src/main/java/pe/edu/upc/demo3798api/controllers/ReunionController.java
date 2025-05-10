package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.ReunionDTO;
import pe.edu.upc.demo3798api.entities.MensajesChat;
import pe.edu.upc.demo3798api.entities.Reunion;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.servicesinterfaces.IMensajesChatService;
import pe.edu.upc.demo3798api.servicesinterfaces.IReunionService;
import pe.edu.upc.demo3798api.servicesinterfaces.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/reuniones")
public class ReunionController {

    @Autowired
    private IReunionService reunionService;

    @Autowired
    private IMensajesChatService mensajeService;

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<Reunion> all() {
        return reunionService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reunion> one(@PathVariable int id) {
        Reunion r = reunionService.listId(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<Reunion> create(@RequestBody ReunionDTO dto) {
        Users paciente = userService.listId(dto.idPaciente);
        Users psicologo = userService.listId(dto.idPsicologo);
        if (paciente == null || psicologo == null)
            return ResponseEntity.badRequest().build();

        Reunion r = new Reunion();
        r.setPaciente(paciente);           // asegúrate de tener campo paciente en Reunion
        r.setPsicologo(psicologo);
        r.setFechaInicio(dto.fechaInicio);
        r.setFechaFin(dto.fechaFin);
        r.setTipo(dto.tipo);

        Reunion saved = reunionService.insert(r);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reunion> update(
            @PathVariable int id,
            @RequestBody ReunionDTO dto) {

        Reunion existing = reunionService.listId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Si cambió el paciente, recarga y valida
        if (!existing.getPaciente().getId().equals(dto.idPaciente)) {
            Users paciente = userService.listId(dto.idPaciente);
            if (paciente == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            existing.setPaciente(paciente);
        }

        // Si cambió el psicólogo, recarga y valida
        if (!existing.getPsicologo().getId().equals(dto.idPsicologo)) {
            Users psicologo = userService.listId(dto.idPsicologo);
            if (psicologo == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            existing.setPsicologo(psicologo);
        }

        // Actualiza las fechas y tipo
        existing.setFechaInicio(dto.fechaInicio);
        existing.setFechaFin(dto.fechaFin);
        existing.setTipo(dto.tipo);

        Reunion saved = reunionService.update(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        reunionService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
