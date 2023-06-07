package br.com.uniamerica.apsystem20.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "Produtos", schema = "public")
public class Produto extends AbstractEntity{

    @Getter @Setter
    @Column(name = "nome_produto", unique = true, length = 50)
    private String nomeProduto;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_produto")
    private Tipo tipo;

    @Getter @Setter
    @Column(name = "codigo_produto", unique = true)
    private String codigoProduto;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fornecedores_do_produto")
    private Fornecedor fornecedor;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "estoqueDoProduto")
    private Estoque estoque;


}
