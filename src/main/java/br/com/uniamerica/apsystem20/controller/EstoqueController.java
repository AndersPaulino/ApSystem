package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.EstoqueRepository;
import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import br.com.uniamerica.apsystem20.service.EstoqueService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/estoque")
public class EstoqueController {
    private final EstoqueService estoqueService;
    private final EstoqueRepository estoqueRepository;

    private final ProdutoRepository produtoRepository;

    @Autowired
    public EstoqueController(EstoqueService estoqueService, EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
        this.estoqueService = estoqueService;
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Estoque> findById(@PathVariable Long id) {
        Optional<Estoque> estoque = estoqueRepository.findById(id);
        if (estoque.isPresent()) {
            return ResponseEntity.ok().body(estoque.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nomeEstoque}")
    public ResponseEntity<?> findByNomeEstoque(@PathVariable String nomeEstoque) {
        List<Estoque> estoques = estoqueRepository.findByNomeEstoque(nomeEstoque);
        if (estoques.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(estoques);
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo) {
        List<Estoque> estoques = estoqueRepository.findByAtivo(ativo);
        if (estoques.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(estoques);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Estoque> estoques = estoqueRepository.findAll();
        if (estoques.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(estoques);
        }
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Estoque estoque) {
        try {
            estoqueService.cadastrar(estoque);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable @NotNull Long id, @RequestBody Estoque estoque) {
        try {
            estoqueService.atualizar(id, estoque);
            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        final Estoque estoque = this.estoqueRepository.findById(id).orElse(null);
        if (estoque != null) {
            List<Produto> produtosVinculados = this.produtoRepository.findByEstoque(estoque);
            if (produtosVinculados.isEmpty()) {
                this.estoqueRepository.delete(estoque);
                return ResponseEntity.ok("Registro deletado com sucesso!");
            } else {
                return ResponseEntity.badRequest().body("Não é possível deletar o estoque, pois existem produtos vinculados a ele.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
