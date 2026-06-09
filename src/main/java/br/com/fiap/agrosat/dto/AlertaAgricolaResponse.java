package br.com.fiap.agrosat.dto;

import java.time.LocalDateTime;

public record AlertaAgricolaResponse(
        Long id,
        Long idTalhao,
        Long idPropriedade,
        String tipo,
        String severidade,
        String mensagem,
        LocalDateTime dataHora,
        String resolvido
) {
}
