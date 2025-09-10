// controller/AuthController.java
package proyecto.contabilidad.controller;
import proyecto.contabilidad.config.JwtUtil;
import proyecto.contabilidad.dto.LoginRequest;
import proyecto.contabilidad.entity.Usuario;
import proyecto.contabilidad.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UsuarioService usuarioService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.usuarioService = usuarioService;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest req) {
    Usuario u = usuarioService.findByUsername(req.getUsername());
    if (u == null || !passwordEncoder.matches(req.getPassword(), u.getPassword())) {
      return ResponseEntity.status(401).body("Credenciales inválidas");
    }
    String token = jwtUtil.generateToken(u.getUsername());
    return ResponseEntity.ok(new proyecto.contabilidad.dto.LoginResponse(token));
  }
}
