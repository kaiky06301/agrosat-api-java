package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RECOMENDACAO")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recomendacao")
    @SequenceGenerator(name = "seq_recomendacao", sequenceName = "SEQ_RECOMENDACAO", allocationSize = 1)
    @Column(name = "id_recomendacao")
    private Long id;

    @Column(name = "id_talhao", nullable = false)
    private Long idTalhao;

    @Column(name = "id_alerta")
    private Long idAlerta;

    @Column(name = "tipo", length = 40)
    private String tipo;

    @Column(name = "texto", length = 255)
    private String texto;

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

    public Long getIdTalhao() { return idTalhao; }
    public void setIdTalhao(Long idTalhao) { this.idTalhao = idTalhao; }

    public Long getIdAlerta() { return idAlerta; }
    public void setIdAlerta(Long idAlerta) { this.idAlerta = idAlerta; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
