package br.com.uniamerica.apsystem20.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "estoques", schema = "public")
public class Estoque extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome_estoque", unique = true, length = 50)
    private String nomeEstoque;

    @Getter @Setter
    @Column(name = "descricao_estoque", length = 250)
    private String descricaoEstoque;

}
