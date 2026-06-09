package br.com.fiap.agrosat.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IrrigacaoResponse(
        Long id,
        Long idTalhao,
        LocalDateTime inicio,
        LocalDateTime fim,
        BigDecimal volumeLitros,
        String modo
) {
}
