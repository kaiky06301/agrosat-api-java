package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.IrrigacaoRequest;
import br.com.fiap.agrosat.dto.IrrigacaoResponse;
import br.com.fiap.agrosat.entity.Irrigacao;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.IrrigacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IrrigacaoService {

    private final IrrigacaoRepository repository;

    public IrrigacaoService(IrrigacaoRepository repository) {
        this.repository = repository;
    }

    public List<IrrigacaoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public IrrigacaoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public IrrigacaoResponse criar(IrrigacaoRequest req) {
        Irrigacao i = new Irrigacao();
        aplicar(i, req);
        return toResponse(repository.save(i));
    }

    public IrrigacaoResponse atualizar(Long id, IrrigacaoRequest req) {
        Irrigacao i = buscarEntidade(id);
        aplicar(i, req);
        return toResponse(repository.save(i));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Irrigacao nao encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private Irrigacao buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao nao encontrada: " + id));
    }

    private void aplicar(Irrigacao i, IrrigacaoRequest req) {
        i.setIdTalhao(req.idTalhao());
        i.setInicio(req.inicio());
        i.setFim(req.fim());
        i.setVolumeLitros(req.volumeLitros());
        if (req.modo() != null) {
            i.setModo(req.modo());
        }
    }

    private IrrigacaoResponse toResponse(Irrigacao i) {
        return new IrrigacaoResponse(i.getId(), i.getIdTalhao(), i.getInicio(),
                i.getFim(), i.getVolumeLitros(), i.getModo());
    }
}
