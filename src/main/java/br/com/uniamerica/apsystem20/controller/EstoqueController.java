package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.repository.EstoqueRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/estoque")
public class EstoqueController {
    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueController(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
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
    public ResponseEntity<?> cadastrar(@RequestBody final Estoque estoque) {
        this.estoqueRepository.save(estoque);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Estoque estoque) {
        Optional<Estoque> estoqueExistente = estoqueRepository.findById(id);

        if (estoqueExistente.isPresent()) {
            Estoque estoqueAtualizado = estoqueExistente.get();
            estoqueAtualizado.setDescricaoEstoque(estoque.getDescricaoEstoque());
            estoqueAtualizado.setNomeEstoque(estoque.getNomeEstoque());
            estoqueAtualizado.setProdutoList(estoque.getProdutoList());

            estoqueRepository.save(estoqueAtualizado);

            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Id invalido!");
        }
    }
}
