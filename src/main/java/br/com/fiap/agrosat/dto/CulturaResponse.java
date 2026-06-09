package br.com.fiap.agrosat.dto;

import java.math.BigDecimal;

public record CulturaResponse(
        Long id,
        String nome,
        BigDecimal umidadeIdealMin,
        BigDecimal umidadeIdealMax,
        BigDecimal tempIdealMin,
        BigDecimal tempIdealMax,
        Integer cicloDias
) {
}
