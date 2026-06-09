package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.AlertaAgricolaRequest;
import br.com.fiap.agrosat.dto.AlertaAgricolaResponse;
import br.com.fiap.agrosat.service.AlertaAgricolaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Alertas")
@RestController
@RequestMapping("/api/alertas")
public class AlertaAgricolaController {

    private final AlertaAgricolaService service;

    public AlertaAgricolaController(AlertaAgricolaService service) {
        this.service = service;
    }

    @GetMapping
    public List<AlertaAgricolaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AlertaAgricolaResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<AlertaAgricolaResponse> criar(@Valid @RequestBody AlertaAgricolaRequest req) {
        AlertaAgricolaResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/alertas/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public AlertaAgricolaResponse atualizar(@PathVariable Long id, @Valid @RequestBody AlertaAgricolaRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
