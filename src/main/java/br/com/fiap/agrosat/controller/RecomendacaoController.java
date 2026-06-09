package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.RecomendacaoRequest;
import br.com.fiap.agrosat.dto.RecomendacaoResponse;
import br.com.fiap.agrosat.service.RecomendacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Recomendacoes")
@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {

    private final RecomendacaoService service;

    public RecomendacaoController(RecomendacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecomendacaoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public RecomendacaoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<RecomendacaoResponse> criar(@Valid @RequestBody RecomendacaoRequest req) {
        RecomendacaoResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/recomendacoes/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public RecomendacaoResponse atualizar(@PathVariable Long id, @Valid @RequestBody RecomendacaoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
