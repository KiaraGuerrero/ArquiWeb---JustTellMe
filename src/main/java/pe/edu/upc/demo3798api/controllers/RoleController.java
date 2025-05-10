package pe.edu.upc.demo3798api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.RoleDTO;
import pe.edu.upc.demo3798api.entities.Role;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.servicesinterfaces.IRoleService;
import pe.edu.upc.demo3798api.servicesinterfaces.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired private IUserService userService;

    @GetMapping
    public List<Role> all() { return roleService.list(); }

    @GetMapping("/{id}")
    public ResponseEntity<Role> one(@PathVariable Long id) {
        Role r = roleService.listId(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody RoleDTO dto) {
        Users u = userService.listId(dto.userId);
        if (u == null) return ResponseEntity.notFound().build();
        Role r = new Role();
        r.setRol(dto.rol);
        r.setUser(u);
        Role saved = roleService.insert(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody RoleDTO dto) {
        Role r = roleService.listId(id);
        if (r == null) return ResponseEntity.notFound().build();
        r.setRol(dto.rol);
        if (!r.getUser().getId().equals(dto.userId)) {
            Users u = userService.listId(dto.userId);
            if (u == null) return ResponseEntity.notFound().build();
            r.setUser(u);
        }
        Role saved = roleService.update(r);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
