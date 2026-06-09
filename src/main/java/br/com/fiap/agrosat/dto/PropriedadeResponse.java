package br.com.fiap.agrosat.dto;

import java.math.BigDecimal;

public record PropriedadeResponse(
        Long id,
        String nome,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal areaTotalHa,
        String municipio,
        String uf,
        Long idUsuario
) {
}
