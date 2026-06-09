package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * HERANCA (modelagem avancada) — classe base abstrata mapeada com estrategia
 * SINGLE_TABLE + coluna discriminadora. As subclasses EstacaoMeteorologica e
 * ControladorIrrigacao herdam o id, o nome e o Endereco embutido.
 */
@Entity
@Table(name = "EQUIPAMENTO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_equipamento", discriminatorType = DiscriminatorType.STRING, length = 20)
@Getter
@Setter
public abstract class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_equipamento")
    @SequenceGenerator(name = "seq_equipamento", sequenceName = "SEQ_EQUIPAMENTO", allocationSize = 1)
    @Column(name = "id_equipamento")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    /** @Embedded — reaproveita o objeto de valor Endereco. */
    @Embedded
    private Endereco endereco;

    /** Cada subtipo informa sua categoria. */
    public abstract String getTipo();
}
