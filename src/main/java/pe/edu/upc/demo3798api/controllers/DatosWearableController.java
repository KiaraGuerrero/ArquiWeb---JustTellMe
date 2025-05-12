package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.DatosWearableDTO;
import pe.edu.upc.demo3798api.entities.DatosWearable;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.servicesinterfaces.IDatosWearableService;
import pe.edu.upc.demo3798api.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/datos")
public class DatosWearableController {

    @Autowired
    private IDatosWearableService datosService;

    @Autowired
    private IUserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','PACIENTE')")
    @GetMapping
    public List<DatosWearable> all(Authentication auth) {
        String username = auth.getName();
        boolean isAdmin    = auth.getAuthorities().stream()
                .anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"));
        List<DatosWearable> todos = datosService.list();
        if (isAdmin) {
            return todos;
        } else {
            // PACIENTE: filtro por username
            return todos.stream()
                    .filter(d -> d.getUsuario().getUsername().equals(username))
                    .collect(Collectors.toList());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','PACIENTE')")
    @PostMapping
    public ResponseEntity<DatosWearable> create(@RequestBody DatosWearableDTO dto) {

        Users u = userService.listId(dto.idUsuario);
        if (u == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        DatosWearable d = new DatosWearable();
        d.setUsuario(u);
        d.setFechaRegistro(dto.fechaRegistro);
        d.setFrecuenciaCardiaca(dto.frecuenciaCardiaca);
        d.setCalidadSueno(dto.calidadSueno);
        d.setNivelActividad(dto.nivelActividad);

        DatosWearable saved = datosService.insert(d);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PreAuthorize("hasAnyRole('PACIENTE') and @datosAuth.isOwner(principal.username, #id)")
    @PutMapping("/{id}")
    public ResponseEntity<DatosWearable> update(
            @PathVariable int id,
            @RequestBody DatosWearableDTO dto) {

        DatosWearable existing = datosService.listId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.getUsuario().getId().equals(dto.idUsuario)) {
            Users u = userService.listId(dto.idUsuario);
            if (u == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }
            existing.setUsuario(u);
        }

        existing.setFechaRegistro(dto.fechaRegistro);
        existing.setFrecuenciaCardiaca(dto.frecuenciaCardiaca);
        existing.setCalidadSueno(dto.calidadSueno);
        existing.setNivelActividad(dto.nivelActividad);

        DatosWearable saved = datosService.update(existing);
        return ResponseEntity.ok(saved);
    }



    @PreAuthorize("hasRole('ADMIN') or @datosAuth.isOwner(principal.username, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<DatosWearable> one(@PathVariable int id) {
        DatosWearable d = datosService.listId(id);
        if (d == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(d);
    }


    @PreAuthorize("hasRole('ADMIN') or @datosAuth.isOwner(principal.username, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        datosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or @datosAuth.isUser(principal.username, #userId)")
    @GetMapping("/report/promedio-fc/{id}")
    public ResponseEntity<List<Map<LocalDate,Double>>> promFcDia(
            @PathVariable("id") Long userId) {
        List<Map<LocalDate,Double>> lista = datosService.promedioFrecuenciaPorDia(userId);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasRole('ADMIN') or @datosAuth.isUser(principal.username, #id)")
    @GetMapping("/report/max-fc/{id}")
    public Integer maxFc(@PathVariable Long id) {
        return datosService.maxFrecuenciaCardiaca(id);
    }
}
