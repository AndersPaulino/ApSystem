package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/produto")
public class ProdutoController {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok().body(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nomeProduto}")
    public ResponseEntity<?> findByNomeProduto(@PathVariable String nomeProduto) {
        List<Produto> produtos = produtoRepository.findByNomeProduto(nomeProduto);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(produtos);
        }
    }
    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo) {
        List<Produto> produtos = produtoRepository.findByAtivo(ativo);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(produtos);
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(produtos);
        }
    }
    @GetMapping("/codigo/{codigoProduto}")
    public ResponseEntity<?> findByCodigoProduto(@PathVariable String codigoProduto){
        List<Produto> produtos = produtoRepository.findByCodigoProduto(codigoProduto);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(produtos);
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Produto produto) {
        this.produtoRepository.save(produto);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Produto produto) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produtoAtualizado = produtoExistente.get();
            produtoAtualizado.setNomeProduto(produto.getNomeProduto());
            produtoAtualizado.setCodigoProduto(produto.getCodigoProduto());
            produtoAtualizado.setAtualizar(produto.getAtualizar());
            produtoAtualizado.setTipo(produto.getTipo());
            produtoAtualizado.setEstoque(produto.getEstoque());
            produtoAtualizado.setFornecedor(produto.getFornecedor());

            try {
                produtoRepository.save(produtoAtualizado);
                return ResponseEntity.ok().body("Registro atualizado com sucesso!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
            }
        } else {
            return ResponseEntity.badRequest().body("Id inválido!");
        }
    }

}
