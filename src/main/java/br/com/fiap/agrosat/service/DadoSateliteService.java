package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.DadoSateliteRequest;
import br.com.fiap.agrosat.dto.DadoSateliteResponse;
import br.com.fiap.agrosat.entity.DadoSatelite;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.DadoSateliteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DadoSateliteService {

    private final DadoSateliteRepository repository;

    public DadoSateliteService(DadoSateliteRepository repository) {
        this.repository = repository;
    }

    public List<DadoSateliteResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public DadoSateliteResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public DadoSateliteResponse criar(DadoSateliteRequest req) {
        DadoSatelite d = new DadoSatelite();
        aplicar(d, req);
        return toResponse(repository.save(d));
    }

    public DadoSateliteResponse atualizar(Long id, DadoSateliteRequest req) {
        DadoSatelite d = buscarEntidade(id);
        aplicar(d, req);
        return toResponse(repository.save(d));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Dado de satelite nao encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private DadoSatelite buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dado de satelite nao encontrado: " + id));
    }

    private void aplicar(DadoSatelite d, DadoSateliteRequest req) {
        d.setIdTalhao(req.idTalhao());
        d.setNdvi(req.ndvi());
        d.setUmidadeEstimada(req.umidadeEstimada());
        d.setIndiceChuvaMm(req.indiceChuvaMm());
        d.setDataReferencia(req.dataReferencia());
    }

    private DadoSateliteResponse toResponse(DadoSatelite d) {
        return new DadoSateliteResponse(d.getId(), d.getIdTalhao(), d.getNdvi(),
                d.getUmidadeEstimada(), d.getIndiceChuvaMm(), d.getDataReferencia());
    }
}
