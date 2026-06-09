package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.LoginRequest;
import br.com.fiap.agrosat.dto.LoginResponse;
import br.com.fiap.agrosat.entity.Usuario;
import br.com.fiap.agrosat.repository.UsuarioRepository;
import br.com.fiap.agrosat.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticacao")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                          UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.senha()));
        String token = jwtService.gerarToken(auth.getName());
        // Inclui id e nome do usuario (o app mobile precisa do idUsuario para o CRUD)
        Usuario u = usuarioRepository.findByEmail(auth.getName()).orElse(null);
        Long idUsuario = (u != null) ? u.getId() : null;
        String nome = (u != null) ? u.getNome() : null;
        return new LoginResponse(token, "Bearer", auth.getName(), idUsuario, nome, jwtService.getExpiration());
    }
}
