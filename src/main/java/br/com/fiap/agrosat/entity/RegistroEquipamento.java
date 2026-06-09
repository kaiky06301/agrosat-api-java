package br.com.fiap.agrosat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Telemetria de um equipamento, identificada por CHAVE COMPOSTA (@EmbeddedId).
 */
@Entity
@Table(name = "REGISTRO_EQUIPAMENTO")
@Getter
@Setter
@NoArgsConstructor
public class RegistroEquipamento {

    @EmbeddedId
    private RegistroEquipamentoId id;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "bateria_pct")
    private Integer bateriaPct;
}
