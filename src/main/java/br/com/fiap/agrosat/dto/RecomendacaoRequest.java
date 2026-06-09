package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record RecomendacaoRequest(
        @NotNull(message = "idTalhao e obrigatorio")
        Long idTalhao,

        Long idAlerta,

        @Pattern(regexp = "IRRIGAR|AGUARDAR|PROTEGER_GEADA|DRENAR",
                message = "tipo invalido")
        String tipo,

        @Size(max = 255)
        String texto,

        LocalDateTime dataHora
) {
}
