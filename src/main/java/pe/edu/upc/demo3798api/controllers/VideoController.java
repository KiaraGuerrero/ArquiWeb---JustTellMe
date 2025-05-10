package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.VideoDTO;
import pe.edu.upc.demo3798api.entities.Video;
import pe.edu.upc.demo3798api.servicesinterfaces.IVideoService;

import java.util.List;


@RestController
@RequestMapping("/api/videos")
public class VideoController {
    @Autowired private IVideoService videoService;

    @GetMapping
    public List<Video> all() { return videoService.list(); }

    @GetMapping("/{id}")
    public ResponseEntity<Video> one(@PathVariable int id) {
        Video v = videoService.listId(id);
        if (v == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(v);
    }

    @PostMapping
    public ResponseEntity<Video> create(@RequestBody VideoDTO dto) {
        Video v = new Video();
        v.setUrl(dto.url);
        v.setTitulo(dto.titulo);
        v.setDescripcion(dto.descripcion);
        v.setCategoria(dto.categoria);
        Video saved = videoService.insert(v);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> update(@PathVariable int id, @RequestBody VideoDTO dto) {
        Video v = videoService.listId(id);
        if (v == null) return ResponseEntity.notFound().build();
        v.setUrl(dto.url);
        v.setTitulo(dto.titulo);
        v.setDescripcion(dto.descripcion);
        v.setCategoria(dto.categoria);
        return ResponseEntity.ok(videoService.update(v));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        videoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

