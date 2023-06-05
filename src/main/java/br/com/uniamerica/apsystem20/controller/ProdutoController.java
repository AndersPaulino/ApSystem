package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import br.com.uniamerica.apsystem20.service.ProdutoService;
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

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository, ProdutoService produtoService) {
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
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

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<?> findByTipoProduto(@PathVariable Long tipoId) {
        List<Produto> produtos = produtoRepository.findByTipoProduto(tipoId);
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(produtos);
        }
    }


    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Produto produto) {
        this.produtoService.cadastrar(produto);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Produto produto) {
        Optional<Produto> produtoExistente = produtoService.findById(id);

        if (produtoExistente.isPresent()) {
            Produto produtoAtualizado = produtoExistente.get();
            try {
                produtoService.atualizarProduto(id,produto);
                return ResponseEntity.ok().body("Registro atualizado com sucesso!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
            }
        } else {
            return ResponseEntity.badRequest().body("Id inválido!");
        }
    }

}
