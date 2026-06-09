package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record PropriedadeRequest(
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 120)
        String nome,

        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal areaTotalHa,

        @Size(max = 80)
        String municipio,

        @Size(min = 2, max = 2, message = "uf deve ter 2 letras")
        String uf,

        @NotNull(message = "idUsuario e obrigatorio")
        Long idUsuario
) {
}
