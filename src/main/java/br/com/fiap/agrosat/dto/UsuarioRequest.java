package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 120)
        String nome,

        @NotBlank(message = "cpf e obrigatorio")
        @Size(min = 11, max = 11, message = "cpf deve ter 11 digitos")
        String cpf,

        @NotBlank(message = "email e obrigatorio")
        @Email(message = "email invalido")
        @Size(max = 120)
        String email,

        @NotBlank(message = "senha e obrigatoria")
        @Size(max = 255)
        String senha,

        @Size(max = 20)
        String telefone
) {
}
