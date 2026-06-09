package br.com.fiap.agrosat.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TALHAO")
public class Talhao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_talhao")
    @SequenceGenerator(name = "seq_talhao", sequenceName = "SEQ_TALHAO", allocationSize = 1)
    @Column(name = "id_talhao")
    private Long id;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "area_ha", precision = 10, scale = 2)
    private BigDecimal areaHa;

    @Column(name = "id_propriedade", nullable = false)
    private Long idPropriedade;

    @Column(name = "id_cultura")
    private Long idCultura;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getAreaHa() { return areaHa; }
    public void setAreaHa(BigDecimal areaHa) { this.areaHa = areaHa; }

    public Long getIdPropriedade() { return idPropriedade; }
    public void setIdPropriedade(Long idPropriedade) { this.idPropriedade = idPropriedade; }

    public Long getIdCultura() { return idCultura; }
    public void setIdCultura(Long idCultura) { this.idCultura = idCultura; }
}
