package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.repository.FornecedorRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorRepository fornecedorRepository;


    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.fornecedorRepository.findById(id).orElse(new Fornecedor()));
    }

    @GetMapping("/{nomeFornecedor}")
    public ResponseEntity<?> findByNomeFornecedor(@PathVariable String nomeFornecedor){
        return ResponseEntity.ok().body(this.fornecedorRepository.findByNomeFornecedor(nomeFornecedor));
    }
    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Fornecedor> fornecedores = this.fornecedorRepository.findByAtivo(ativo);

        if (fornecedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(fornecedores);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Fornecedor> fornecedores = this.fornecedorRepository.findAll();

        if (fornecedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(fornecedores);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Fornecedor fornecedor) {
        this.fornecedorRepository.save(fornecedor);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Fornecedor fornecedor) {
        if (id.equals(fornecedor.getId()) && !this.fornecedorRepository.findById(id).isEmpty()) {
            this.fornecedorRepository.save(fornecedor);
        } else {
            return ResponseEntity.badRequest().body("Id nao foi encontrado");
        }
        return ResponseEntity.ok().body("Registro atualizado com sucesso");
    }
}
