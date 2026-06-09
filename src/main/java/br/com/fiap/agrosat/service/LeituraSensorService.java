package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.LeituraSensorRequest;
import br.com.fiap.agrosat.dto.LeituraSensorResponse;
import br.com.fiap.agrosat.entity.LeituraSensor;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.LeituraSensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraSensorService {

    private final LeituraSensorRepository repository;

    public LeituraSensorService(LeituraSensorRepository repository) {
        this.repository = repository;
    }

    public List<LeituraSensorResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public LeituraSensorResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public LeituraSensorResponse criar(LeituraSensorRequest req) {
        LeituraSensor l = new LeituraSensor();
        aplicar(l, req);
        return toResponse(repository.save(l));
    }

    public LeituraSensorResponse atualizar(Long id, LeituraSensorRequest req) {
        LeituraSensor l = buscarEntidade(id);
        aplicar(l, req);
        return toResponse(repository.save(l));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Leitura nao encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private LeituraSensor buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura nao encontrada: " + id));
    }

    private void aplicar(LeituraSensor l, LeituraSensorRequest req) {
        l.setIdSensor(req.idSensor());
        l.setUmidadeSolo(req.umidadeSolo());
        l.setTemperatura(req.temperatura());
        l.setUmidadeAr(req.umidadeAr());
        l.setLuminosidade(req.luminosidade());
        if (req.dataHora() != null) {
            l.setDataHora(req.dataHora());
        }
    }

    private LeituraSensorResponse toResponse(LeituraSensor l) {
        return new LeituraSensorResponse(l.getId(), l.getIdSensor(), l.getUmidadeSolo(),
                l.getTemperatura(), l.getUmidadeAr(), l.getLuminosidade(), l.getDataHora());
    }
}
