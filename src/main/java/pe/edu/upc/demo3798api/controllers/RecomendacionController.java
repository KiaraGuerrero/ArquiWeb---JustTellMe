package pe.edu.upc.demo3798api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.RecomendacionDTO;
import pe.edu.upc.demo3798api.entities.Recomendacion;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.entities.Video;
import pe.edu.upc.demo3798api.servicesinterfaces.IRecomendacionService;
import pe.edu.upc.demo3798api.servicesinterfaces.IUserService;
import pe.edu.upc.demo3798api.servicesinterfaces.IVideoService;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    private final IRecomendacionService recService;
    private final IUserService userService;
    private final IVideoService videoService;

    public RecomendacionController(IRecomendacionService recService,
                                   IUserService userService,
                                   IVideoService videoService) {
        this.recService = recService;
        this.userService = userService;
        this.videoService = videoService;
    }

    @GetMapping
    public List<Recomendacion> all() {
        return recService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recomendacion> one(@PathVariable Integer id) {
        Recomendacion r = recService.listId(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<Recomendacion> create(@RequestBody RecomendacionDTO dto) {
        // validar usuario
        Users u = userService.listId(dto.idUsuario);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Video v = videoService.listId(dto.idVideo);
        if (v == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Recomendacion r = new Recomendacion();
        r.setUsuario(u);
        r.setVideo(v);
        r.setFechaRecomendacion(dto.fechaRecomendacion);
        r.setEstado(dto.estado);

        Recomendacion saved = recService.insert(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recomendacion> update(@PathVariable Integer id,
                                                @RequestBody RecomendacionDTO dto) {
        Recomendacion existing = recService.listId(id);
        if (existing == null) return ResponseEntity.notFound().build();


        if (!existing.getUsuario().getId().equals(dto.idUsuario)) {
            Users u = userService.listId(dto.idUsuario);
            if (u == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            existing.setUsuario(u);
        }

        if (!existing.getVideo().getIdVideo().equals(dto.idVideo)) {
            Video v = videoService.listId(dto.idVideo);
            if (v == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            existing.setVideo(v);
        }

        existing.setFechaRecomendacion(dto.fechaRecomendacion);
        existing.setEstado(dto.estado);

        Recomendacion saved = recService.update(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        recService.delete(id);
        return ResponseEntity.noContent().build();
    }
}