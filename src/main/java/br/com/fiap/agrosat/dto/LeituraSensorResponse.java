package br.com.fiap.agrosat.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LeituraSensorResponse(
        Long id,
        Long idSensor,
        BigDecimal umidadeSolo,
        BigDecimal temperatura,
        BigDecimal umidadeAr,
        BigDecimal luminosidade,
        LocalDateTime dataHora
) {
}
