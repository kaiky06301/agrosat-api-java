package br.com.fiap.agrosat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/** Subtipo de Equipamento (herança SINGLE_TABLE, discriminador CONTROLADOR). */
@Entity
@DiscriminatorValue("CONTROLADOR")
@Getter
@Setter
public class ControladorIrrigacao extends Equipamento {

    @Column(name = "num_valvulas")
    private Integer numValvulas;

    @Override
    public String getTipo() {
        return "CONTROLADOR_IRRIGACAO";
    }
}
