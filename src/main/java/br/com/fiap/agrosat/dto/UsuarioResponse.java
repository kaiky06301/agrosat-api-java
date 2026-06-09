package br.com.fiap.agrosat.dto;

import java.time.LocalDate;

public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataCadastro
) {
}
