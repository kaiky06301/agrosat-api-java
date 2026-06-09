package br.com.fiap.agrosat.config;

import br.com.fiap.agrosat.entity.*;
import br.com.fiap.agrosat.repository.EquipamentoRepository;
import br.com.fiap.agrosat.repository.RegistroEquipamentoRepository;
import br.com.fiap.agrosat.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Cria dados de teste nos perfis H2 e Postgres, para permitir login e demonstracao
 * imediatos apos subir a aplicacao (inclusive no deploy em nuvem com banco vazio).
 * Login: admin@agrosat.com.br / senha: 123456
 */
@Configuration
@Profile({"h2", "postgres"})
public class DataSeeder {

    @Bean
    public CommandLineRunner seed(UsuarioRepository usuarioRepository,
                                  EquipamentoRepository equipamentoRepository,
                                  RegistroEquipamentoRepository registroRepository,
                                  PasswordEncoder passwordEncoder) {
        return args -> {
            // 1) Usuario admin para login imediato
            if (usuarioRepository.findByEmail("admin@agrosat.com.br").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Admin AgroSat");
                admin.setCpf("00000000000");
                admin.setEmail("admin@agrosat.com.br");
                admin.setSenha(passwordEncoder.encode("123456"));
                admin.setTelefone("11999999999");
                usuarioRepository.save(admin);
            }

            // 2) Equipamentos (herança + @Embedded Endereco) e registro (chave composta)
            if (equipamentoRepository.count() == 0) {
                EstacaoMeteorologica estacao = new EstacaoMeteorologica();
                estacao.setNome("Estacao Campo Norte");
                estacao.setEndereco(new Endereco("Estrada Rural km 12", "Ribeirao Preto", "SP", "14000-000"));
                estacao.setAlcanceKm(new BigDecimal("5.50"));
                estacao = equipamentoRepository.save(estacao);

                ControladorIrrigacao controlador = new ControladorIrrigacao();
                controlador.setNome("Controlador Pivo 1");
                controlador.setEndereco(new Endereco("Estrada Rural km 13", "Ribeirao Preto", "SP", "14000-000"));
                controlador.setNumValvulas(8);
                equipamentoRepository.save(controlador);

                RegistroEquipamento registro = new RegistroEquipamento();
                registro.setId(new RegistroEquipamentoId(estacao.getId(), LocalDateTime.now()));
                registro.setStatus("ONLINE");
                registro.setBateriaPct(92);
                registroRepository.save(registro);
            }
        };
    }
}
