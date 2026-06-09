package br.com.fiap.agrosat.dto;

import java.time.LocalDateTime;

public record RecomendacaoResponse(
        Long id,
        Long idTalhao,
        Long idAlerta,
        String tipo,
        String texto,
        LocalDateTime dataHora
) {
}
