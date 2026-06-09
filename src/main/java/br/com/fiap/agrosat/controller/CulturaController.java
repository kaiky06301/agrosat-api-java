package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.CulturaRequest;
import br.com.fiap.agrosat.dto.CulturaResponse;
import br.com.fiap.agrosat.service.CulturaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Culturas")
@RestController
@RequestMapping("/api/culturas")
public class CulturaController {

    private final CulturaService service;

    public CulturaController(CulturaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CulturaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public CulturaResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<CulturaResponse> criar(@Valid @RequestBody CulturaRequest req) {
        CulturaResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/culturas/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public CulturaResponse atualizar(@PathVariable Long id, @Valid @RequestBody CulturaRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
