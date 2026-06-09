package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.CulturaRequest;
import br.com.fiap.agrosat.dto.CulturaResponse;
import br.com.fiap.agrosat.entity.Cultura;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.CulturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CulturaService {

    private final CulturaRepository repository;

    public CulturaService(CulturaRepository repository) {
        this.repository = repository;
    }

    public List<CulturaResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CulturaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public CulturaResponse criar(CulturaRequest req) {
        Cultura c = new Cultura();
        aplicar(c, req);
        return toResponse(repository.save(c));
    }

    public CulturaResponse atualizar(Long id, CulturaRequest req) {
        Cultura c = buscarEntidade(id);
        aplicar(c, req);
        return toResponse(repository.save(c));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cultura nao encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private Cultura buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cultura nao encontrada: " + id));
    }

    private void aplicar(Cultura c, CulturaRequest req) {
        c.setNome(req.nome());
        c.setUmidadeIdealMin(req.umidadeIdealMin());
        c.setUmidadeIdealMax(req.umidadeIdealMax());
        c.setTempIdealMin(req.tempIdealMin());
        c.setTempIdealMax(req.tempIdealMax());
        c.setCicloDias(req.cicloDias());
    }

    private CulturaResponse toResponse(Cultura c) {
        return new CulturaResponse(c.getId(), c.getNome(), c.getUmidadeIdealMin(),
                c.getUmidadeIdealMax(), c.getTempIdealMin(), c.getTempIdealMax(), c.getCicloDias());
    }
}
