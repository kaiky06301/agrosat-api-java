package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CulturaRequest(
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 80)
        String nome,

        BigDecimal umidadeIdealMin,
        BigDecimal umidadeIdealMax,
        BigDecimal tempIdealMin,
        BigDecimal tempIdealMax,
        Integer cicloDias
) {
}
