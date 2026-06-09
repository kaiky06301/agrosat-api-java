package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.TalhaoRequest;
import br.com.fiap.agrosat.dto.TalhaoResponse;
import br.com.fiap.agrosat.service.TalhaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Talhoes")
@RestController
@RequestMapping("/api/talhoes")
public class TalhaoController {

    private final TalhaoService service;

    public TalhaoController(TalhaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TalhaoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TalhaoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<TalhaoResponse> criar(@Valid @RequestBody TalhaoRequest req) {
        TalhaoResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/talhoes/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public TalhaoResponse atualizar(@PathVariable Long id, @Valid @RequestBody TalhaoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
