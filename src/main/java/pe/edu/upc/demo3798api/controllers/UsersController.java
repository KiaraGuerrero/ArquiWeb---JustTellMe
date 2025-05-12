package pe.edu.upc.demo3798api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.demo3798api.dtos.*;
import pe.edu.upc.demo3798api.entities.Role;
import pe.edu.upc.demo3798api.entities.Users;
import pe.edu.upc.demo3798api.securities.JwtTokenUtil;
import pe.edu.upc.demo3798api.servicesimplements.JwtUserDetailsService;
import pe.edu.upc.demo3798api.servicesinterfaces.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsersController {

    @Autowired private IUserService userService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<MessageDTO> register(@RequestBody UsersDTO dto) {
        if (userService.existsByUsername(dto.username)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageDTO("El username ya existe"));
        }
        Users u = new Users();
        u.setUsername(dto.username);
        u.setPassword(passwordEncoder.encode(dto.password));
        u.setEnabled(dto.enabled);
        u.setRoles(dto.roles.stream().map(rn -> {
            Role r = new Role();
            r.setRol(rn);
            r.setUser(u);
            return r;
        }).collect(Collectors.toList()));

        userService.insert(u);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageDTO("Usuario creado correctamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails ud = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtTokenUtil.generateToken(ud);
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserPassDTO> listar(){

        return userService.list().stream().map(x->{
            ModelMapper m = new ModelMapper();
            return m.map(x, UserPassDTO.class);
        }).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserPassDTO buscarId(@PathVariable("id") Long id){
        ModelMapper m = new ModelMapper();
        UserPassDTO dto=m.map(userService.listId(id), UserPassDTO.class);
        return dto;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public void modificar(@RequestBody UsersDTO dto){
        ModelMapper m = new ModelMapper();
        Users a = m.map(dto, Users.class);
        userService.update(a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
