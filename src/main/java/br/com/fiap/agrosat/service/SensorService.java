package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.SensorRequest;
import br.com.fiap.agrosat.dto.SensorResponse;
import br.com.fiap.agrosat.entity.Sensor;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private final SensorRepository repository;

    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }

    public List<SensorResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SensorResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public SensorResponse criar(SensorRequest req) {
        Sensor s = new Sensor();
        aplicar(s, req);
        return toResponse(repository.save(s));
    }

    public SensorResponse atualizar(Long id, SensorRequest req) {
        Sensor s = buscarEntidade(id);
        aplicar(s, req);
        return toResponse(repository.save(s));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Sensor nao encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private Sensor buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor nao encontrado: " + id));
    }

    private void aplicar(Sensor s, SensorRequest req) {
        s.setCodigo(req.codigo());
        s.setTipo(req.tipo());
        if (req.status() != null) {
            s.setStatus(req.status());
        }
        if (req.dataInstalacao() != null) {
            s.setDataInstalacao(req.dataInstalacao());
        }
        s.setIdTalhao(req.idTalhao());
    }

    private SensorResponse toResponse(Sensor s) {
        return new SensorResponse(s.getId(), s.getCodigo(), s.getTipo(), s.getStatus(),
                s.getDataInstalacao(), s.getIdTalhao());
    }
}
