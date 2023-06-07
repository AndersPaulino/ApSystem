package br.com.uniamerica.apsystem20.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "movimentacoes", schema = "public")
public class Movimentacao extends AbstractEntity{

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "produtos")
    private Produto produto;

    @Getter @Setter
    @Max(value = 999999)
    @Column(name = "quantidade_produto")
    private Integer quantidade;

    @Getter @Setter
    @Column(name = "saida_produto")
    private Integer saida;

    @Getter @Setter
    @Column(name = "total_produtos")
    private Integer totalProduto;

    @Getter @Setter
    @Digits(integer = 9, fraction = 2)
    @Column(name = "valor_compra")
    private BigDecimal valorCompra;

    @Getter @Setter
    @Digits(integer = 9, fraction = 2)
    @Column(name = "valor_venda")
    private BigDecimal valorVenda;

    @Getter @Setter
    @Digits(integer = 9, fraction = 2)
    @Column(name = "valor_total_vendas")
    private BigDecimal valorTotal;
}
