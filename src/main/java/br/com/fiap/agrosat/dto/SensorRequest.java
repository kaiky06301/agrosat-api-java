package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record SensorRequest(
        @NotBlank(message = "codigo e obrigatorio")
        @Size(max = 40)
        String codigo,

        @Size(max = 40)
        String tipo,

        @Pattern(regexp = "ATIVO|INATIVO", message = "status deve ser ATIVO ou INATIVO")
        String status,

        LocalDate dataInstalacao,

        @NotNull(message = "idTalhao e obrigatorio")
        Long idTalhao
) {
}
