package br.com.fiap.agrosat.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadoSateliteResponse(
        Long id,
        Long idTalhao,
        BigDecimal ndvi,
        BigDecimal umidadeEstimada,
        BigDecimal indiceChuvaMm,
        LocalDate dataReferencia
) {
}
