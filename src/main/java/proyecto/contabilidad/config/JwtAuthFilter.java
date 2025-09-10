// config/JwtAuthFilter.java
package proyecto.contabilidad.config;
import proyecto.contabilidad.service.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UsuarioService usuarioService; // servicio que devuelve Usuario

  public JwtAuthFilter(JwtUtil jwtUtil, UsuarioService usuarioService) {
    this.jwtUtil = jwtUtil;
    this.usuarioService = usuarioService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      if (jwtUtil.validateToken(token)) {
        String username = jwtUtil.extractUsername(token);
        // obtener usuario
        var user = usuarioService.findByUsername(username);
        // crear auth simple (mejor crear GrantedAuthorities según rol)
        var auth = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
    filterChain.doFilter(request, response);
  }
}
