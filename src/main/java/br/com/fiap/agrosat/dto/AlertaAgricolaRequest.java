package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record AlertaAgricolaRequest(
        @NotNull(message = "idTalhao e obrigatorio")
        Long idTalhao,

        Long idPropriedade,

        @Pattern(regexp = "SECA|GEADA|EXCESSO_UMIDADE|RISCO_CLIMATICO",
                message = "tipo invalido")
        String tipo,

        @Pattern(regexp = "BAIXA|MEDIA|ALTA", message = "severidade invalida")
        String severidade,

        @Size(max = 255)
        String mensagem,

        LocalDateTime dataHora,

        @Pattern(regexp = "S|N", message = "resolvido deve ser S ou N")
        String resolvido
) {
}
