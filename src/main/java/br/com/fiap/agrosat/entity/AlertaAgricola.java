package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA_AGRICOLA")
public class AlertaAgricola {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alerta")
    @SequenceGenerator(name = "seq_alerta", sequenceName = "SEQ_ALERTA", allocationSize = 1)
    @Column(name = "id_alerta")
    private Long id;

    @Column(name = "id_talhao", nullable = false)
    private Long idTalhao;

    // Facilita o filtro por fazenda no app mobile (denormalizado do talhao).
    @Column(name = "id_propriedade")
    private Long idPropriedade;

    @Column(name = "tipo", length = 30)
    private String tipo;

    @Column(name = "severidade", length = 20)
    private String severidade;

    @Column(name = "mensagem", length = 255)
    private String mensagem;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "resolvido", length = 1, columnDefinition = "CHAR(1)")
    private String resolvido;

    @PrePersist
    public void prePersist() {
        if (dataHora == null) {
            dataHora = LocalDateTime.now();
        }
        if (resolvido == null) {
            resolvido = "N";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdTalhao() { return idTalhao; }
    public void setIdTalhao(Long idTalhao) { this.idTalhao = idTalhao; }

    public Long getIdPropriedade() { return idPropriedade; }
    public void setIdPropriedade(Long idPropriedade) { this.idPropriedade = idPropriedade; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getSeveridade() { return severidade; }
    public void setSeveridade(String severidade) { this.severidade = severidade; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getResolvido() { return resolvido; }
    public void setResolvido(String resolvido) { this.resolvido = resolvido; }
}
