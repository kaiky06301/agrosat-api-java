package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.DadoSateliteRequest;
import br.com.fiap.agrosat.dto.DadoSateliteResponse;
import br.com.fiap.agrosat.service.DadoSateliteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Dados de Satelite")
@RestController
@RequestMapping("/api/dados-satelite")
public class DadoSateliteController {

    private final DadoSateliteService service;

    public DadoSateliteController(DadoSateliteService service) {
        this.service = service;
    }

    @GetMapping
    public List<DadoSateliteResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public DadoSateliteResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<DadoSateliteResponse> criar(@Valid @RequestBody DadoSateliteRequest req) {
        DadoSateliteResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/dados-satelite/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public DadoSateliteResponse atualizar(@PathVariable Long id, @Valid @RequestBody DadoSateliteRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
