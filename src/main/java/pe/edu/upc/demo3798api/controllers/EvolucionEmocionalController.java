package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.EvolucionEmocionalDTO;
import pe.edu.upc.demo3798api.entities.EvolucionEmocional;
import pe.edu.upc.demo3798api.servicesinterfaces.IEvolucionEmocionalService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/evoluciones")
public class EvolucionEmocionalController {

    @Autowired
    private IEvolucionEmocionalService evolucionService;

    @GetMapping
    public List<EvolucionEmocional> all() {
        return evolucionService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvolucionEmocional> one(@PathVariable int id) {
        EvolucionEmocional e = evolucionService.listId(id);
        if (e == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<EvolucionEmocional> create(@RequestBody EvolucionEmocionalDTO dto) {
        EvolucionEmocional e = evolucionService.crearEvolucion(dto.idPaciente, dto.estadoEmocional, dto.observaciones, dto.fechaRegistro);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvolucionEmocional> update(@PathVariable int id, @RequestBody EvolucionEmocionalDTO dto) {
        EvolucionEmocional actual = evolucionService.listId(id);
        if (actual == null) return ResponseEntity.notFound().build();
        EvolucionEmocional actualizado = evolucionService.crearEvolucion(dto.idPaciente, dto.estadoEmocional, dto.observaciones, dto.fechaRegistro);
        actualizado.setIdEvolucion(id);
        return ResponseEntity.ok(evolucionService.update(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        evolucionService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
