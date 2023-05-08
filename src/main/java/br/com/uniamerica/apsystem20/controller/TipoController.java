package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.entity.Tipo;
import br.com.uniamerica.apsystem20.repository.TipoRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/tipo")
public class TipoController {
    @Autowired
    TipoRepository tipoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.tipoRepository.findById(id).orElse(new Tipo()));
    }
    @GetMapping("/{nomeTipo}")
    public ResponseEntity<?> findByNomeTipo(@PathVariable String nomeTipo){
        return ResponseEntity.ok().body(this.tipoRepository.findByNomeTipo(nomeTipo));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        List<Tipo> tipos = this.tipoRepository.findByAtivo(ativo);

        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(tipos);
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Tipo> tipos = this.tipoRepository.findAll();

        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(tipos);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Tipo tipo ){
        this.tipoRepository.save(tipo);
        return ResponseEntity.ok().body("Registro cadastrado com sucesso");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Tipo tipo) {
        if (id.equals(tipo.getId()) && !this.tipoRepository.findById(id).isEmpty()) {
            this.tipoRepository.save(tipo);
        } else {
            return ResponseEntity.badRequest().body("Id nao foi encontrado");
        }
        return ResponseEntity.ok().body("Registro atualizado com sucesso");
    }
}
