package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.FornecedorRepository;
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
    private final FornecedorRepository fornecedorRepository;

    @Autowired
    public FornecedorController(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> findById(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            return ResponseEntity.ok().body(fornecedor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nomeFornecedor}")
    public ResponseEntity<?> findByNomeFornecedor(@PathVariable String nomeFornecedor) {
        List<Fornecedor> fornecedors = fornecedorRepository.findByNomeFornecedor(nomeFornecedor);
        if (fornecedors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(fornecedors);
        }
    }
    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo) {
        List<Fornecedor> fornecedors = fornecedorRepository.findByAtivo(ativo);
        if (fornecedors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(fornecedors);
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Fornecedor> fornecedors = fornecedorRepository.findAll();
        if (fornecedors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(fornecedors);
        }
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Fornecedor fornecedor) {
        this.fornecedorRepository.save(fornecedor);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Fornecedor fornecedor) {
        Optional<Fornecedor> fornecedorExistente = fornecedorRepository.findById(id);

        if (fornecedorExistente.isPresent()) {
            Fornecedor fornecedorAtualizado = fornecedorExistente.get();
            fornecedorAtualizado.setNomeFornecedor(fornecedor.getNomeFornecedor());
            fornecedorAtualizado.setTelefoneFornecedor(fornecedor.getTelefoneFornecedor());
            fornecedorAtualizado.setEmail(fornecedor.getEmail());

            try {
                fornecedorRepository.save(fornecedorAtualizado);
                return ResponseEntity.ok().body("Registro atualizado com sucesso!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
            }
        } else {
            return ResponseEntity.badRequest().body("Id inválido!");
        }
    }
}
