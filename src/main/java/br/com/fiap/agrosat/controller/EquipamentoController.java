package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.entity.Equipamento;
import br.com.fiap.agrosat.repository.EquipamentoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Expoe os equipamentos (estacoes meteorologicas e controladores de irrigacao),
 * demonstrando a HERANCA JPA: a mesma listagem retorna os dois subtipos com o
 * campo "tipo" identificando cada um.
 */
@Tag(name = "Equipamentos (herança/embedded/chave composta)")
@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    private final EquipamentoRepository repository;

    public EquipamentoController(EquipamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Equipamento> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
