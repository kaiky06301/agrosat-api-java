package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.PropriedadeRequest;
import br.com.fiap.agrosat.dto.PropriedadeResponse;
import br.com.fiap.agrosat.service.PropriedadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Propriedades")
@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {

    private final PropriedadeService service;

    public PropriedadeController(PropriedadeService service) {
        this.service = service;
    }

    @GetMapping
    public List<PropriedadeResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PropriedadeResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<PropriedadeResponse> criar(@Valid @RequestBody PropriedadeRequest req) {
        PropriedadeResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/propriedades/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public PropriedadeResponse atualizar(@PathVariable Long id, @Valid @RequestBody PropriedadeRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
