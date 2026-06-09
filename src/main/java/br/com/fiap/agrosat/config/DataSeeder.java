package br.com.fiap.agrosat.config;

import br.com.fiap.agrosat.entity.*;
import br.com.fiap.agrosat.repository.EquipamentoRepository;
import br.com.fiap.agrosat.repository.PropriedadeRepository;
import br.com.fiap.agrosat.repository.RegistroEquipamentoRepository;
import br.com.fiap.agrosat.repository.TalhaoRepository;
import br.com.fiap.agrosat.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dados de teste (perfis H2 e Postgres) para login e demonstração imediatos.
 * Contas:
 *   admin@agrosat.com.br / 123456
 *   kaiky@boavista.agr.br / soja2026   (Fazenda Boa Vista, Fazenda Cerrado)
 *   felipe@cafedoalto.agr.br / cafe2026 (Sitio Cafe do Alto)
 */
@Configuration
@Profile({"h2", "postgres"})
public class DataSeeder {

    @Bean
    public CommandLineRunner seed(UsuarioRepository usuarioRepo,
                                  PropriedadeRepository propriedadeRepo,
                                  TalhaoRepository talhaoRepo,
                                  EquipamentoRepository equipamentoRepo,
                                  RegistroEquipamentoRepository registroRepo,
                                  PasswordEncoder enc) {
        return args -> {
            // admin
            if (usuarioRepo.findByEmail("admin@agrosat.com.br").isEmpty()) {
                usuarioRepo.save(novoUsuario("Admin AgroSat", "00000000000", "admin@agrosat.com.br", enc.encode("123456")));
            }

            // Contas + dados do vídeo (Kaiky e Felipe)
            if (usuarioRepo.findByEmail("kaiky@boavista.agr.br").isEmpty()) {
                Usuario kaiky = usuarioRepo.save(novoUsuario("Kaiky Oliveira", "11111111111", "kaiky@boavista.agr.br", enc.encode("soja2026")));

                Propriedade boaVista = propriedadeRepo.save(novaProp(kaiky.getId(), "Fazenda Boa Vista", "Campinas/SP", 320));
                talhaoRepo.save(novoTalhao(boaVista.getId(), "Talhao Norte", 80, "Soja", 38, 40, 70, 0.42));
                talhaoRepo.save(novoTalhao(boaVista.getId(), "Talhao Leste", 65, "Milho", 58, 45, 75, 0.61));
                talhaoRepo.save(novoTalhao(boaVista.getId(), "Talhao Sul", 90, "Cafe", 73, 45, 65, 0.68));

                Propriedade cerrado = propriedadeRepo.save(novaProp(kaiky.getId(), "Fazenda Cerrado", "Rio Verde/GO", 210));
                talhaoRepo.save(novoTalhao(cerrado.getId(), "Talhao Chapada", 120, "Soja", 52, 40, 70, 0.59));
                talhaoRepo.save(novoTalhao(cerrado.getId(), "Talhao Baixao", 90, "Milho", 44, 45, 75, 0.48));
            }

            if (usuarioRepo.findByEmail("felipe@cafedoalto.agr.br").isEmpty()) {
                Usuario felipe = usuarioRepo.save(novoUsuario("Felipe Souza", "22222222222", "felipe@cafedoalto.agr.br", enc.encode("cafe2026")));

                Propriedade cafe = propriedadeRepo.save(novaProp(felipe.getId(), "Sitio Cafe do Alto", "Patrocinio/MG", 145));
                talhaoRepo.save(novoTalhao(cafe.getId(), "Talhao Cafezal", 55, "Cafe", 64, 50, 70, 0.71));
                talhaoRepo.save(novoTalhao(cafe.getId(), "Talhao Encosta", 40, "Cafe", 33, 50, 70, 0.36));
                talhaoRepo.save(novoTalhao(cafe.getId(), "Talhao do Vale", 50, "Milho", 69, 45, 75, 0.66));
            }

            // Equipamentos (herança + embedded + chave composta)
            if (equipamentoRepo.count() == 0) {
                EstacaoMeteorologica est = new EstacaoMeteorologica();
                est.setNome("Estacao Campo Norte");
                est.setEndereco(new Endereco("Estrada Rural km 12", "Ribeirao Preto", "SP", "14000-000"));
                est.setAlcanceKm(new BigDecimal("5.50"));
                est = equipamentoRepo.save(est);

                ControladorIrrigacao ctrl = new ControladorIrrigacao();
                ctrl.setNome("Controlador Pivo 1");
                ctrl.setEndereco(new Endereco("Estrada Rural km 13", "Ribeirao Preto", "SP", "14000-000"));
                ctrl.setNumValvulas(8);
                equipamentoRepo.save(ctrl);

                RegistroEquipamento reg = new RegistroEquipamento();
                reg.setId(new RegistroEquipamentoId(est.getId(), LocalDateTime.now()));
                reg.setStatus("ONLINE");
                reg.setBateriaPct(92);
                registroRepo.save(reg);
            }
        };
    }

    private Usuario novoUsuario(String nome, String cpf, String email, String senhaCodificada) {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setCpf(cpf);
        u.setEmail(email);
        u.setSenha(senhaCodificada);
        u.setTelefone("11999999999");
        return u;
    }

    private Propriedade novaProp(Long idUsuario, String nome, String local, double ha) {
        Propriedade p = new Propriedade();
        p.setIdUsuario(idUsuario);
        p.setNome(nome);
        p.setMunicipio(local);
        p.setAreaTotalHa(BigDecimal.valueOf(ha));
        return p;
    }

    private Talhao novoTalhao(Long idProp, String nome, double ha, String cultura,
                              double umidade, double idealLo, double idealHi, double ndvi) {
        Talhao t = new Talhao();
        t.setIdPropriedade(idProp);
        t.setNome(nome);
        t.setAreaHa(BigDecimal.valueOf(ha));
        t.setCultura(cultura);
        t.setUmidadeAtual(BigDecimal.valueOf(umidade));
        t.setUmidadeIdealMin(BigDecimal.valueOf(idealLo));
        t.setUmidadeIdealMax(BigDecimal.valueOf(idealHi));
        t.setNdvi(BigDecimal.valueOf(ndvi));
        return t;
    }
}
