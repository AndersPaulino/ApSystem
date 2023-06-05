package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.entity.Tipo;
import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {


    private final ProdutoRepository produtoRepository;
    private final EstoqueService estoqueService;
    private final TipoService tipoService;
    private final FornecedorService fornecedorService;

    public ProdutoService(ProdutoRepository produtoRepository, EstoqueService estoqueService, TipoService tipoService, FornecedorService fornecedorService) {
        this.produtoRepository = produtoRepository;
        this.estoqueService = estoqueService;
        this.tipoService = tipoService;
        this.fornecedorService = fornecedorService;
    }


    public void validarProduto(final Produto produto){
        if (produto.getNomeProduto() == null || produto.getNomeProduto().isEmpty()){
            throw new IllegalArgumentException("Nome do Produto não informado");
        }
        if (!produto.getNomeProduto().matches("[a-zA-Z\\- ]+")) {
            throw new IllegalArgumentException("Nome do Produto inválido");
        }
        if (produto.getCodigoProduto() == null || produto.getCodigoProduto().isEmpty()){
            throw new IllegalArgumentException("Código do produto não informado");
        }
        if (!produto.getCodigoProduto().matches("\\d+")) {
            throw new IllegalArgumentException("Código do Produto inválido");
        }
        if (produto.getSaida() == null){
            produto.setSaida(0);
        }
        int quantidade = produto.getQuantidade() != null ? produto.getQuantidade() : 0;
        int saida = produto.getSaida() != null ? produto.getSaida() : 0;
        produto.setTotalProduto(quantidade - saida);
        BigDecimal valorVenda = produto.getValorVenda() != null ? produto.getValorVenda() : BigDecimal.ZERO;
        produto.setValorTotal(BigDecimal.valueOf(saida).multiply(valorVenda));
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> findByNomeProduto(String nomeProduto) {
        return produtoRepository.findByNomeProduto(nomeProduto);
    }

    public List<Produto> findByAtivo(boolean ativo) {
        return produtoRepository.findByAtivo(ativo);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public void validarProdutoCadastrado(final Produto produto) {
        String codigo = produto.getCodigoProduto();
        String nome = produto.getNomeProduto();
        int saida = produto.getSaida();
        int quantidade = produto.getQuantidade();

        if (nome != null) {
            // Verificar se o nome contém apenas letras maiúsculas, minúsculas e espaço
            if (!nome.matches("[a-zA-Z\\- ]+")) {
                throw new IllegalArgumentException("Nome do produto inválido");
            }
        }

        if (codigo != null) {
            // Verificar se o código contém apenas números
            if (!codigo.matches("\\d+")) {
                throw new IllegalArgumentException("Código do produto inválido");
            }
        }

        if (saida != 0) {
            int estoque = quantidade != 0 ? quantidade : 0;
            int saidaProduto = saida != 0 ? saida : 0;
            produto.setTotalProduto(estoque - saidaProduto);
            BigDecimal valorVenda = produto.getValorVenda() != null ? produto.getValorVenda() : BigDecimal.ZERO;
            produto.setValorTotal(BigDecimal.valueOf(saidaProduto).multiply(valorVenda));
        }

        if (nome == null && codigo == null && quantidade == 0 && saida == 0 && produto.getEstoque() == null && produto.getFornecedor() == null && produto.getTipo() == null) {
            throw new IllegalArgumentException("Nenhum campo do produto informado");
        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrar(final Produto produto){
        this.validarProduto(produto);
        this.produtoRepository.save(produto);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizarProduto(final Long id, final Produto produto) {
        validarProdutoCadastrado(produto);
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produtoAtualizado = produtoExistente.get();

            if (produto.getNomeProduto() != null) {
                produtoAtualizado.setNomeProduto(produto.getNomeProduto());
            }

            if (produto.getCodigoProduto() != null) {
                produtoAtualizado.setCodigoProduto(produto.getCodigoProduto());
            }

            if (produto.getTipo() != null) {
                produtoAtualizado.setTipo(produto.getTipo());
            }

            if (produto.getFornecedor() != null) {
                produtoAtualizado.setFornecedor(produto.getFornecedor());
            }

            if (produto.getEstoque() != null) {
                produtoAtualizado.setEstoque(produto.getEstoque());
            }

            if (produto.getSaida() != null) {
                produtoAtualizado.setSaida(produto.getSaida());
            }

            produtoRepository.save(produtoAtualizado);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }
}
