package proyecto.contabilidad.controller;

import proyecto.contabilidad.dto.UsuarioDTO;
import proyecto.contabilidad.entity.Rol;
import proyecto.contabilidad.entity.Usuario;
import proyecto.contabilidad.repository.RolRepository;
import proyecto.contabilidad.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        var usuarios = usuarioRepo.findAll().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getUsername(), u.getRol().getNombre()))
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestParam String username,
                                            @RequestParam String password,
                                            @RequestParam String rol) {
        Rol rolEntity = rolRepo.findAll().stream()
                .filter(r -> r.getNombre().equalsIgnoreCase(rol))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rol no existe: " + rol));

        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(encoder.encode(password));
        u.setRol(rolEntity);
        usuarioRepo.save(u);

        return ResponseEntity.ok(new UsuarioDTO(u.getId(), u.getUsername(), rolEntity.getNombre()));
    }
}
