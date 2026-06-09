package br.com.fiap.agrosat.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LeituraSensorRequest(
        // idSensor OU idTalhao (o app mobile envia por talhao)
        Long idSensor,
        Long idTalhao,

        BigDecimal umidadeSolo,
        BigDecimal temperatura,
        BigDecimal umidadeAr,
        BigDecimal luminosidade,
        LocalDateTime dataHora
) {
}
