package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DadoSateliteRequest(
        @NotNull(message = "idTalhao e obrigatorio")
        Long idTalhao,

        @DecimalMin(value = "-1.0", message = "ndvi minimo e -1")
        @DecimalMax(value = "1.0", message = "ndvi maximo e 1")
        BigDecimal ndvi,

        BigDecimal umidadeEstimada,
        BigDecimal indiceChuvaMm,
        LocalDate dataReferencia
) {
}
