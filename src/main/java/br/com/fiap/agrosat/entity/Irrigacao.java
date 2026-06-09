package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "IRRIGACAO")
public class Irrigacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_irrigacao")
    @SequenceGenerator(name = "seq_irrigacao", sequenceName = "SEQ_IRRIGACAO", allocationSize = 1)
    @Column(name = "id_irrigacao")
    private Long id;

    @Column(name = "id_talhao", nullable = false)
    private Long idTalhao;

    @Column(name = "inicio")
    private LocalDateTime inicio;

    @Column(name = "fim")
    private LocalDateTime fim;

    @Column(name = "volume_litros", precision = 10, scale = 2)
    private BigDecimal volumeLitros;

    @Column(name = "modo", length = 10)
    private String modo;

    @PrePersist
    public void prePersist() {
        if (modo == null) {
            modo = "AUTO";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdTalhao() { return idTalhao; }
    public void setIdTalhao(Long idTalhao) { this.idTalhao = idTalhao; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }

    public BigDecimal getVolumeLitros() { return volumeLitros; }
    public void setVolumeLitros(BigDecimal volumeLitros) { this.volumeLitros = volumeLitros; }

    public String getModo() { return modo; }
    public void setModo(String modo) { this.modo = modo; }
}
