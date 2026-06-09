package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.UsuarioRequest;
import br.com.fiap.agrosat.dto.UsuarioResponse;
import br.com.fiap.agrosat.entity.Usuario;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public UsuarioResponse criar(UsuarioRequest req) {
        Usuario u = new Usuario();
        aplicar(u, req);
        u.setSenha(passwordEncoder.encode(req.senha()));
        return toResponse(repository.save(u));
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest req) {
        Usuario u = buscarEntidade(id);
        aplicar(u, req);
        u.setSenha(passwordEncoder.encode(req.senha()));
        return toResponse(repository.save(u));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario nao encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private Usuario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado: " + id));
    }

    private void aplicar(Usuario u, UsuarioRequest req) {
        u.setNome(req.nome());
        u.setCpf(req.cpf());
        u.setEmail(req.email());
        u.setTelefone(req.telefone());
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getCpf(), u.getEmail(),
                u.getTelefone(), u.getDataCadastro());
    }
}
