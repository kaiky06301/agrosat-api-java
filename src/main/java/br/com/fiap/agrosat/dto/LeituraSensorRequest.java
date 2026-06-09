package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LeituraSensorRequest(
        @NotNull(message = "idSensor e obrigatorio")
        Long idSensor,

        BigDecimal umidadeSolo,
        BigDecimal temperatura,
        BigDecimal umidadeAr,
        BigDecimal luminosidade,
        LocalDateTime dataHora
) {
}
