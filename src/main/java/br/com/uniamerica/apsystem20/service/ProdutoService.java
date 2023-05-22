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

        Assert.isTrue(produto.getNomeProduto() != null,
                "Nome do produto nao informado");

        Assert.isTrue(produto.getCodigoProduto() != null,
                "Codigo do produto nao informado");
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

        if(id.equals(produto.getId()) && !this.produtoRepository.findById(id).isEmpty()){
            this.validarProduto(produto);
            this.produtoRepository.save(produto);
        }
        else{
            throw new RuntimeException("Id nao encontrado.");
        }

    }
}
