package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LEITURA_SENSOR")
public class LeituraSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_leitura")
    @SequenceGenerator(name = "seq_leitura", sequenceName = "SEQ_LEITURA", allocationSize = 1)
    @Column(name = "id_leitura")
    private Long id;

    @Column(name = "id_sensor", nullable = false)
    private Long idSensor;

    @Column(name = "umidade_solo", precision = 5, scale = 2)
    private BigDecimal umidadeSolo;

    @Column(name = "temperatura", precision = 5, scale = 2)
    private BigDecimal temperatura;

    @Column(name = "umidade_ar", precision = 5, scale = 2)
    private BigDecimal umidadeAr;

    @Column(name = "luminosidade", precision = 7, scale = 2)
    private BigDecimal luminosidade;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @PrePersist
    public void prePersist() {
        if (dataHora == null) {
            dataHora = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdSensor() { return idSensor; }
    public void setIdSensor(Long idSensor) { this.idSensor = idSensor; }

    public BigDecimal getUmidadeSolo() { return umidadeSolo; }
    public void setUmidadeSolo(BigDecimal umidadeSolo) { this.umidadeSolo = umidadeSolo; }

    public BigDecimal getTemperatura() { return temperatura; }
    public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }

    public BigDecimal getUmidadeAr() { return umidadeAr; }
    public void setUmidadeAr(BigDecimal umidadeAr) { this.umidadeAr = umidadeAr; }

    public BigDecimal getLuminosidade() { return luminosidade; }
    public void setLuminosidade(BigDecimal luminosidade) { this.luminosidade = luminosidade; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
