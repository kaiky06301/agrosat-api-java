package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.PropriedadeRequest;
import br.com.fiap.agrosat.dto.PropriedadeResponse;
import br.com.fiap.agrosat.service.PropriedadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Propriedades")
@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {

    private final PropriedadeService service;

    public PropriedadeController(PropriedadeService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<EntityModel<PropriedadeResponse>> listar() {
        List<EntityModel<PropriedadeResponse>> itens = service.listar().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(itens,
                linkTo(methodOn(PropriedadeController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<PropriedadeResponse> buscar(@PathVariable Long id) {
        return toModel(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<PropriedadeResponse>> criar(@Valid @RequestBody PropriedadeRequest req) {
        PropriedadeResponse criado = service.criar(req);
        EntityModel<PropriedadeResponse> model = toModel(criado);
        return ResponseEntity
                .created(linkTo(methodOn(PropriedadeController.class).buscar(criado.id())).toUri())
                .body(model);
    }

    @PutMapping("/{id}")
    public EntityModel<PropriedadeResponse> atualizar(@PathVariable Long id, @Valid @RequestBody PropriedadeRequest req) {
        return toModel(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /** Monta o recurso com links HATEOAS (self, coleção, atualizar, excluir). */
    private EntityModel<PropriedadeResponse> toModel(PropriedadeResponse p) {
        return EntityModel.of(p,
                linkTo(methodOn(PropriedadeController.class).buscar(p.id())).withSelfRel(),
                linkTo(methodOn(PropriedadeController.class).listar()).withRel("propriedades"),
                linkTo(methodOn(PropriedadeController.class).atualizar(p.id(), null)).withRel("atualizar"),
                linkTo(methodOn(PropriedadeController.class).deletar(p.id())).withRel("excluir"));
    }
}
