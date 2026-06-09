package br.com.fiap.agrosat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * CHAVE COMPOSTA (modelagem avancada) — identificador embutido (@EmbeddedId)
 * formado por (id do equipamento + instante do registro).
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RegistroEquipamentoId implements Serializable {

    @Column(name = "id_equipamento")
    private Long idEquipamento;

    @Column(name = "instante")
    private LocalDateTime instante;
}
