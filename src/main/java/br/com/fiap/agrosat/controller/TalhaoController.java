package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.TalhaoRequest;
import br.com.fiap.agrosat.dto.TalhaoResponse;
import br.com.fiap.agrosat.service.TalhaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Talhoes")
@RestController
@RequestMapping("/api/talhoes")
public class TalhaoController {

    private final TalhaoService service;

    public TalhaoController(TalhaoService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<EntityModel<TalhaoResponse>> listar() {
        List<EntityModel<TalhaoResponse>> itens = service.listar().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(itens,
                linkTo(methodOn(TalhaoController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<TalhaoResponse> buscar(@PathVariable Long id) {
        return toModel(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<TalhaoResponse>> criar(@Valid @RequestBody TalhaoRequest req) {
        TalhaoResponse criado = service.criar(req);
        return ResponseEntity
                .created(linkTo(methodOn(TalhaoController.class).buscar(criado.id())).toUri())
                .body(toModel(criado));
    }

    @PutMapping("/{id}")
    public EntityModel<TalhaoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody TalhaoRequest req) {
        return toModel(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<TalhaoResponse> toModel(TalhaoResponse t) {
        return EntityModel.of(t,
                linkTo(methodOn(TalhaoController.class).buscar(t.id())).withSelfRel(),
                linkTo(methodOn(TalhaoController.class).listar()).withRel("talhoes"),
                linkTo(methodOn(TalhaoController.class).atualizar(t.id(), null)).withRel("atualizar"),
                linkTo(methodOn(TalhaoController.class).deletar(t.id())).withRel("excluir"));
    }
}
