package br.com.uniamerica.apsystem20.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Produtos", schema = "public")
public class Produto extends AbstractEntity{

    @Getter @Setter
    @Column(name = "nomeProduto", unique = true, length = 50)
    private String nomeProduto;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tipoProduto")
    private Tipo tipo;

    @Getter @Setter
    @Column(name = "codigoProduto", unique = true)
    private String codigoProduto;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fornecedoresDoProduto")
    private Fornecedor fornecedor;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "estoqueDoProduto")
    private Estoque estoque;
}
