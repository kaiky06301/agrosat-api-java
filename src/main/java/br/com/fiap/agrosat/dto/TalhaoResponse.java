package br.com.fiap.agrosat.dto;

import java.math.BigDecimal;

public record TalhaoResponse(
        Long id,
        String nome,
        BigDecimal areaHa,
        Long idPropriedade,
        Long idCultura
) {
}
