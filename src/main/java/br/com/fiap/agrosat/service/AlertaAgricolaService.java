package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.AlertaAgricolaRequest;
import br.com.fiap.agrosat.dto.AlertaAgricolaResponse;
import br.com.fiap.agrosat.entity.AlertaAgricola;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.AlertaAgricolaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaAgricolaService {

    private final AlertaAgricolaRepository repository;

    public AlertaAgricolaService(AlertaAgricolaRepository repository) {
        this.repository = repository;
    }

    public List<AlertaAgricolaResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AlertaAgricolaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public AlertaAgricolaResponse criar(AlertaAgricolaRequest req) {
        AlertaAgricola a = new AlertaAgricola();
        aplicar(a, req);
        return toResponse(repository.save(a));
    }

    public AlertaAgricolaResponse atualizar(Long id, AlertaAgricolaRequest req) {
        AlertaAgricola a = buscarEntidade(id);
        aplicar(a, req);
        return toResponse(repository.save(a));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Alerta nao encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private AlertaAgricola buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta nao encontrado: " + id));
    }

    private void aplicar(AlertaAgricola a, AlertaAgricolaRequest req) {
        a.setIdTalhao(req.idTalhao());
        a.setIdPropriedade(req.idPropriedade());
        a.setTipo(req.tipo());
        a.setSeveridade(req.severidade());
        a.setMensagem(req.mensagem());
        if (req.dataHora() != null) {
            a.setDataHora(req.dataHora());
        }
        if (req.resolvido() != null) {
            a.setResolvido(req.resolvido());
        }
    }

    private AlertaAgricolaResponse toResponse(AlertaAgricola a) {
        return new AlertaAgricolaResponse(a.getId(), a.getIdTalhao(), a.getIdPropriedade(), a.getTipo(),
                a.getSeveridade(), a.getMensagem(), a.getDataHora(), a.getResolvido());
    }
}
