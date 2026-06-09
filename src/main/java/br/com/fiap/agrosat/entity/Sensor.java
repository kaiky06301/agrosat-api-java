package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SENSOR")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sensor")
    @SequenceGenerator(name = "seq_sensor", sequenceName = "SEQ_SENSOR", allocationSize = 1)
    @Column(name = "id_sensor")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 40)
    private String codigo;

    @Column(name = "tipo", length = 40)
    private String tipo;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Column(name = "id_talhao", nullable = false)
    private Long idTalhao;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "ATIVO";
        }
        if (dataInstalacao == null) {
            dataInstalacao = LocalDate.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDataInstalacao() { return dataInstalacao; }
    public void setDataInstalacao(LocalDate dataInstalacao) { this.dataInstalacao = dataInstalacao; }

    public Long getIdTalhao() { return idTalhao; }
    public void setIdTalhao(Long idTalhao) { this.idTalhao = idTalhao; }
}
