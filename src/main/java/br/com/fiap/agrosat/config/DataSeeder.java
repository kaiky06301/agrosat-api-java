package br.com.fiap.agrosat.config;

import br.com.fiap.agrosat.entity.Usuario;
import br.com.fiap.agrosat.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Cria um usuario de teste apenas no perfil H2, para permitir login imediato.
 * Login: admin@agrosat.com.br / senha: 123456
 */
@Configuration
@Profile("h2")
public class DataSeeder {

    @Bean
    public CommandLineRunner seed(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@agrosat.com.br").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Admin AgroSat");
                admin.setCpf("00000000000");
                admin.setEmail("admin@agrosat.com.br");
                admin.setSenha(passwordEncoder.encode("123456"));
                admin.setTelefone("11999999999");
                usuarioRepository.save(admin);
            }
        };
    }
}
