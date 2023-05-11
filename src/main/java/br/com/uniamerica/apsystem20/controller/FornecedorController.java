package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.repository.FornecedorRepository;
import br.com.uniamerica.apsystem20.service.FornecedorService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/fornecedor")
public class FornecedorController {

    private final FornecedorService fornecedorService;
    private final FornecedorRepository fornecedorRepository;

    @Autowired
    public FornecedorController(FornecedorService fornecedorService, FornecedorRepository fornecedorRepository) {
        this.fornecedorService = fornecedorService;
        this.fornecedorRepository = fornecedorRepository;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> findById(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorService.findById(id);
        if (fornecedor.isPresent()) {
            return ResponseEntity.ok().body(fornecedor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/nome/{nomeFornecedor}")
    public ResponseEntity<?> findByNomeFornecedor(@PathVariable String nomeFornecedor) {
        List<Fornecedor> fornecedors = fornecedorService.findByNomeFornecedor(nomeFornecedor);
        if (fornecedors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(fornecedors);
        }
    }
    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo) {
        List<Fornecedor> fornecedors = fornecedorService.findByAtivo(ativo);
        if (fornecedors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(fornecedors);
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Fornecedor> fornecedors = fornecedorService.findAll();
        if (fornecedors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(fornecedors);
        }
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Fornecedor fornecedor) {
        try {
            fornecedorService.cadastrarFornecedor(fornecedor);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable @NotNull Long id, @RequestBody Fornecedor fornecedor) {
        try {
            fornecedorService.atualizarFornecedor(id, fornecedor);
            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
        }
    }
}
