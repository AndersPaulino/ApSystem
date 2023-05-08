package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/produto")
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.produtoRepository.findById(id).orElse(new Produto()));
    }

    @GetMapping("/nome/{nomeProduto}")
    public ResponseEntity<?> findByNomeProduto(@PathVariable String nomeProduto){
        return ResponseEntity.ok().body(this.produtoRepository.findByNomeProduto(nomeProduto));
    }
    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Produto> produtos = this.produtoRepository.findByAtivo(ativo);

        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(produtos);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Produto> produtos = this.produtoRepository.findAll();

        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(produtos);
    }
    @GetMapping("/codigo/{codigoProduto}")
    public ResponseEntity<?> findByCodigoProduto(@PathVariable String codigoProduto){
        return ResponseEntity.ok().body(this.produtoRepository.findByCodigoProduto(codigoProduto));
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
        this.produtoRepository.save(produto);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Produto produto) {
        if (id.equals(produto.getId()) && !this.produtoRepository.findById(id).isEmpty()) {
            this.produtoRepository.save(produto);
        } else {
            return ResponseEntity.badRequest().body("Id nao foi encontrado");
        }
        return ResponseEntity.ok().body("Registro atualizado com sucesso");
    }
}
