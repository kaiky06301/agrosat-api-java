package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PROPRIEDADE")
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_propriedade")
    @SequenceGenerator(name = "seq_propriedade", sequenceName = "SEQ_PROPRIEDADE", allocationSize = 1)
    @Column(name = "id_propriedade")
    private Long id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "area_total_ha", precision = 10, scale = 2)
    private BigDecimal areaTotalHa;

    @Column(name = "municipio", length = 80)
    private String municipio;

    @Column(name = "uf", length = 2, columnDefinition = "CHAR(2)")
    private String uf;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public BigDecimal getAreaTotalHa() { return areaTotalHa; }
    public void setAreaTotalHa(BigDecimal areaTotalHa) { this.areaTotalHa = areaTotalHa; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
}
