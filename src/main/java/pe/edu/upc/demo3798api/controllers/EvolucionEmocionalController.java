package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('PSICOLOGO','ADMIN')")
    @GetMapping
    public List<EvolucionEmocional> all() {
        return evolucionService.list();
    }

    @PreAuthorize("hasAnyRole('PSICOLOGO','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EvolucionEmocional> one(@PathVariable int id) {
        EvolucionEmocional e = evolucionService.listId(id);
        if (e == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PSICOLOGO')")
    @PostMapping
    public ResponseEntity<EvolucionEmocional> create(@RequestBody EvolucionEmocionalDTO dto) {
        EvolucionEmocional e = evolucionService.crearEvolucion(dto.idPaciente, dto.estadoEmocional, dto.observaciones, dto.fechaRegistro);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PSICOLOGO')")
    @PutMapping("/{id}")
    public ResponseEntity<EvolucionEmocional> update(@PathVariable int id, @RequestBody EvolucionEmocionalDTO dto) {
        EvolucionEmocional actual = evolucionService.listId(id);
        if (actual == null) return ResponseEntity.notFound().build();
        EvolucionEmocional actualizado = evolucionService.crearEvolucion(dto.idPaciente, dto.estadoEmocional, dto.observaciones, dto.fechaRegistro);
        actualizado.setIdEvolucion(id);
        return ResponseEntity.ok(evolucionService.update(actualizado));
    }

    @PreAuthorize("hasAnyRole('ADMIN','PSICOLOGO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        evolucionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 1. Todas las evoluciones de un paciente
    @PreAuthorize("hasAnyRole('ADMIN','PSICOLOGO')")
    @GetMapping("/paciente/{id}")
    public List<EvolucionEmocional> porPaciente(@PathVariable Integer id) {
        return evolucionService.listarPorPaciente(id);
    }

    // 2. Evoluciones desde una fecha
    @PreAuthorize("hasAnyRole('PSICOLOGO','ADMIN')")
    @GetMapping("/fecha")
    public List<EvolucionEmocional> desdeFecha(@RequestParam("f") String fecha) {
        LocalDateTime f1 = LocalDateTime.parse(fecha);
        return evolucionService.listarDesdeFecha(f1);
    }

    // 3. Conteo de evoluciones
    @PreAuthorize("hasAnyRole('PSICOLOGO','ADMIN')")
    @GetMapping("/conteo/{id}")
    public int conteoPorPaciente(@PathVariable Long id) {
        return evolucionService.contarPorPaciente(id);
    }

    // 4. Última evolución registrada
    @PreAuthorize("hasAnyRole('PSICOLOGO','ADMIN')")
    @GetMapping("/ultima/{id}")
    public ResponseEntity<EvolucionEmocional> ultima(@PathVariable Long id) {
        EvolucionEmocional ult = evolucionService.obtenerUltimaEvolucion(id);
        if (ult == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ult);
    }


}
