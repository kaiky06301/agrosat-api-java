package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IrrigacaoRequest(
        @NotNull(message = "idTalhao e obrigatorio")
        Long idTalhao,

        LocalDateTime inicio,
        LocalDateTime fim,
        BigDecimal volumeLitros,

        @Pattern(regexp = "AUTO|MANUAL", message = "modo deve ser AUTO ou MANUAL")
        String modo
) {
}
