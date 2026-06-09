package br.com.fiap.agrosat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto de valor EMBUTIDO (@Embeddable) — modelagem avancada com Lombok.
 * Reaproveitado dentro de Equipamento via @Embedded.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Column(name = "end_logradouro", length = 150)
    private String logradouro;

    @Column(name = "end_municipio", length = 80)
    private String municipio;

    @Column(name = "end_uf", length = 2)
    private String uf;

    @Column(name = "end_cep", length = 9)
    private String cep;
}
