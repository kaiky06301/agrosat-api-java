package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.SensorRequest;
import br.com.fiap.agrosat.dto.SensorResponse;
import br.com.fiap.agrosat.service.SensorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Sensores")
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @GetMapping
    public List<SensorResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public SensorResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<SensorResponse> criar(@Valid @RequestBody SensorRequest req) {
        SensorResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/sensores/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public SensorResponse atualizar(@PathVariable Long id, @Valid @RequestBody SensorRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
