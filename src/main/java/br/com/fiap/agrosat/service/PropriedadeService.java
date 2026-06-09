package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.PropriedadeRequest;
import br.com.fiap.agrosat.dto.PropriedadeResponse;
import br.com.fiap.agrosat.entity.Propriedade;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.PropriedadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropriedadeService {

    private final PropriedadeRepository repository;

    public PropriedadeService(PropriedadeRepository repository) {
        this.repository = repository;
    }

    public List<PropriedadeResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public PropriedadeResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public PropriedadeResponse criar(PropriedadeRequest req) {
        Propriedade p = new Propriedade();
        aplicar(p, req);
        return toResponse(repository.save(p));
    }

    public PropriedadeResponse atualizar(Long id, PropriedadeRequest req) {
        Propriedade p = buscarEntidade(id);
        aplicar(p, req);
        return toResponse(repository.save(p));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Propriedade nao encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private Propriedade buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade nao encontrada: " + id));
    }

    private void aplicar(Propriedade p, PropriedadeRequest req) {
        p.setNome(req.nome());
        p.setLatitude(req.latitude());
        p.setLongitude(req.longitude());
        p.setAreaTotalHa(req.areaTotalHa());
        p.setMunicipio(req.municipio());
        p.setUf(req.uf());
        p.setIdUsuario(req.idUsuario());
    }

    private PropriedadeResponse toResponse(Propriedade p) {
        return new PropriedadeResponse(p.getId(), p.getNome(), p.getLatitude(), p.getLongitude(),
                p.getAreaTotalHa(), p.getMunicipio(), p.getUf(), p.getIdUsuario());
    }
}
