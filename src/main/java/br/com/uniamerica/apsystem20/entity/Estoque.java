package br.com.uniamerica.apsystem20.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Estoques", schema = "public")
public class Estoque extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nomeEstoque", unique = true, length = 50)
    private String nomeEstoque;

    @Getter @Setter
    @Column(name = "descricaoEstoque", length = 250)
    private String descricaoEstoque;

    @Getter @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "produtoEstoque",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "estoque.id",
                        "produto.id"
                }
        ),
         joinColumns = @JoinColumn(
                 name = "estoque.id"
         ),
         inverseJoinColumns = @JoinColumn(
                 name = "produto.id"
         )
    )
    private List<Produto> produtoList;
}
