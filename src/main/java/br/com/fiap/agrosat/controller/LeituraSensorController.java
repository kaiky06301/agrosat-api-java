package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.LeituraSensorRequest;
import br.com.fiap.agrosat.dto.LeituraSensorResponse;
import br.com.fiap.agrosat.service.LeituraSensorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Leituras")
@RestController
@RequestMapping("/api/leituras")
public class LeituraSensorController {

    private final LeituraSensorService service;

    public LeituraSensorController(LeituraSensorService service) {
        this.service = service;
    }

    @GetMapping
    public List<LeituraSensorResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public LeituraSensorResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<LeituraSensorResponse> criar(@Valid @RequestBody LeituraSensorRequest req) {
        LeituraSensorResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/leituras/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public LeituraSensorResponse atualizar(@PathVariable Long id, @Valid @RequestBody LeituraSensorRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
