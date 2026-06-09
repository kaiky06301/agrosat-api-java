package br.com.fiap.agrosat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** Subtipo de Equipamento (herança SINGLE_TABLE, discriminador ESTACAO). */
@Entity
@DiscriminatorValue("ESTACAO")
@Getter
@Setter
public class EstacaoMeteorologica extends Equipamento {

    @Column(name = "alcance_km", precision = 6, scale = 2)
    private BigDecimal alcanceKm;

    @Override
    public String getTipo() {
        return "ESTACAO_METEOROLOGICA";
    }
}
