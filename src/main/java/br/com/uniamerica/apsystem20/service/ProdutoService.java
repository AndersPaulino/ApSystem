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

    private void atualizarPropriedadesProduto(Produto produtoAtualizado, Produto produto) {
        if (produto.getNomeProduto() != null) {
            validarNomeProduto(produto.getNomeProduto());
            produtoAtualizado.setNomeProduto(produto.getNomeProduto());
        }

        if (produto.getCodigoProduto() != null) {
            validarCodigoProduto(produto.getCodigoProduto());
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
    }

    public void validarProdutoCadastrado(final Produto produto) {
        validarNomeProduto(produto.getNomeProduto());
        validarCodigoProduto(produto.getCodigoProduto());

        if (produto.getEstoque() == null && produto.getFornecedor() == null && produto.getTipo() == null) {
            throw new IllegalArgumentException("Nenhum campo do produto informado");
        }
    }

    private void validarNomeProduto(String nome) {
        if (nome != null && !nome.matches("[a-zA-Z\\- ]+")) {
            throw new IllegalArgumentException("Nome do produto inválido");
        }
    }

    private void validarCodigoProduto(String codigo) {
        if (codigo != null && !codigo.matches("\\d+")) {
            throw new IllegalArgumentException("Código do produto inválido");
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
        if (!produtoExistente.isPresent()) {
            throw new IllegalArgumentException("Id inválido!");
        }

        Produto produtoAtualizado = produtoExistente.get();
        atualizarPropriedadesProduto(produtoAtualizado, produto);

        produtoRepository.save(produtoAtualizado);
    }
}
