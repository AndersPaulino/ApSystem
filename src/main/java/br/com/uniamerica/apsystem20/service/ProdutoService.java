package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrar(final Produto produto){
        this.validarProduto(produto);
        this.produtoRepository.save(produto);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(final Long id, final Produto produto){
    validarProduto(produto);
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

            produtoRepository.save(produtoAtualizado);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }
}
