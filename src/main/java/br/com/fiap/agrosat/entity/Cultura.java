package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CULTURA")
public class Cultura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cultura")
    @SequenceGenerator(name = "seq_cultura", sequenceName = "SEQ_CULTURA", allocationSize = 1)
    @Column(name = "id_cultura")
    private Long id;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "umidade_ideal_min", precision = 5, scale = 2)
    private BigDecimal umidadeIdealMin;

    @Column(name = "umidade_ideal_max", precision = 5, scale = 2)
    private BigDecimal umidadeIdealMax;

    @Column(name = "temp_ideal_min", precision = 5, scale = 2)
    private BigDecimal tempIdealMin;

    @Column(name = "temp_ideal_max", precision = 5, scale = 2)
    private BigDecimal tempIdealMax;

    @Column(name = "ciclo_dias")
    private Integer cicloDias;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getUmidadeIdealMin() { return umidadeIdealMin; }
    public void setUmidadeIdealMin(BigDecimal umidadeIdealMin) { this.umidadeIdealMin = umidadeIdealMin; }

    public BigDecimal getUmidadeIdealMax() { return umidadeIdealMax; }
    public void setUmidadeIdealMax(BigDecimal umidadeIdealMax) { this.umidadeIdealMax = umidadeIdealMax; }

    public BigDecimal getTempIdealMin() { return tempIdealMin; }
    public void setTempIdealMin(BigDecimal tempIdealMin) { this.tempIdealMin = tempIdealMin; }

    public BigDecimal getTempIdealMax() { return tempIdealMax; }
    public void setTempIdealMax(BigDecimal tempIdealMax) { this.tempIdealMax = tempIdealMax; }

    public Integer getCicloDias() { return cicloDias; }
    public void setCicloDias(Integer cicloDias) { this.cicloDias = cicloDias; }
}
