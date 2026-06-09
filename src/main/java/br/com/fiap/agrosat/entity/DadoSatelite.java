package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DADO_SATELITE")
public class DadoSatelite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dado_sat")
    @SequenceGenerator(name = "seq_dado_sat", sequenceName = "SEQ_DADO_SAT", allocationSize = 1)
    @Column(name = "id_dado_sat")
    private Long id;

    @Column(name = "id_talhao", nullable = false)
    private Long idTalhao;

    @Column(name = "ndvi", precision = 4, scale = 3)
    private BigDecimal ndvi;

    @Column(name = "umidade_estimada", precision = 5, scale = 2)
    private BigDecimal umidadeEstimada;

    @Column(name = "indice_chuva_mm", precision = 6, scale = 2)
    private BigDecimal indiceChuvaMm;

    @Column(name = "data_referencia")
    private LocalDate dataReferencia;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdTalhao() { return idTalhao; }
    public void setIdTalhao(Long idTalhao) { this.idTalhao = idTalhao; }

    public BigDecimal getNdvi() { return ndvi; }
    public void setNdvi(BigDecimal ndvi) { this.ndvi = ndvi; }

    public BigDecimal getUmidadeEstimada() { return umidadeEstimada; }
    public void setUmidadeEstimada(BigDecimal umidadeEstimada) { this.umidadeEstimada = umidadeEstimada; }

    public BigDecimal getIndiceChuvaMm() { return indiceChuvaMm; }
    public void setIndiceChuvaMm(BigDecimal indiceChuvaMm) { this.indiceChuvaMm = indiceChuvaMm; }

    public LocalDate getDataReferencia() { return dataReferencia; }
    public void setDataReferencia(LocalDate dataReferencia) { this.dataReferencia = dataReferencia; }
}
