package br.com.fiap.agrosat.dto;

import java.time.LocalDate;

public record SensorResponse(
        Long id,
        String codigo,
        String tipo,
        String status,
        LocalDate dataInstalacao,
        Long idTalhao
) {
}
