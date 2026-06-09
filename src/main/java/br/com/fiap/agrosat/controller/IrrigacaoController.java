package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.IrrigacaoRequest;
import br.com.fiap.agrosat.dto.IrrigacaoResponse;
import br.com.fiap.agrosat.service.IrrigacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Irrigacoes")
@RestController
@RequestMapping("/api/irrigacoes")
public class IrrigacaoController {

    private final IrrigacaoService service;

    public IrrigacaoController(IrrigacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<IrrigacaoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public IrrigacaoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<IrrigacaoResponse> criar(@Valid @RequestBody IrrigacaoRequest req) {
        IrrigacaoResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/api/irrigacoes/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public IrrigacaoResponse atualizar(@PathVariable Long id, @Valid @RequestBody IrrigacaoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
