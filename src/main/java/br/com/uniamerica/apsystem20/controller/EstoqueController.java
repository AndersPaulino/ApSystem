package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.repository.EstoqueRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/estoque")
public class EstoqueController {
    @Autowired
    EstoqueRepository estoqueRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.estoqueRepository.findById(id).orElse(new Estoque()));
    }

    @GetMapping("/{nomeEstoque}")
    public ResponseEntity<?> findByNomeEstoque(@PathVariable String nomeEstoque){
        return ResponseEntity.ok().body(this.estoqueRepository.findByNomeEstoque(nomeEstoque));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Estoque> estoques = this.estoqueRepository.findByAtivo(ativo);

        if (estoques.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(estoques);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Estoque> estoques = this.estoqueRepository.findAll();

        if (estoques.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(estoques);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Estoque estoque) {
        this.estoqueRepository.save(estoque);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Estoque estoque) {
        if (id.equals(estoque.getId()) && !this.estoqueRepository.findById(id).isEmpty()) {
            this.estoqueRepository.save(estoque);
        } else {
            return ResponseEntity.badRequest().body("Id nao foi encontrado");
        }
        return ResponseEntity.ok().body("Registro atualizado com sucesso");
    }

}
