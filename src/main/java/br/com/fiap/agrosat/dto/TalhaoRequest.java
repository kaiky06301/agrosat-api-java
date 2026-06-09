package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record TalhaoRequest(
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 80)
        String nome,

        BigDecimal areaHa,

        @NotNull(message = "idPropriedade e obrigatorio")
        Long idPropriedade,

        Long idCultura,

        String cultura,
        BigDecimal umidadeAtual,
        BigDecimal umidadeIdealMin,
        BigDecimal umidadeIdealMax,
        BigDecimal ndvi
) {
}
