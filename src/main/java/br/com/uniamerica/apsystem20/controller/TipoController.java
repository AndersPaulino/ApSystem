package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Tipo;
import br.com.uniamerica.apsystem20.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/tipo")
public class TipoController {
    private final TipoRepository tipoRepository;

    @Autowired
    public TipoController(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> findById(@PathVariable Long id) {
        Optional<Tipo> tipo = tipoRepository.findById(id);
        if (tipo.isPresent()) {
            return ResponseEntity.ok().body(tipo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/nome/{nomeTipo}")
    public ResponseEntity<?> findByNomeTipo(@PathVariable String nomeTipo) {
        List<Tipo> tipos = tipoRepository.findByNomeTipo(nomeTipo);
        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tipos);
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo) {
        List<Tipo> tipos = tipoRepository.findByAtivo(ativo);
        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tipos);
        }
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
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Tipo tipo) {
        Optional<Tipo> tipoExistente = tipoRepository.findById(id);

        if (tipoExistente.isPresent()) {
            Tipo tipoAtualizado = tipoExistente.get();
            tipoAtualizado.setNomeTipo(tipo.getNomeTipo());

            tipoRepository.save(tipoAtualizado);

            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Id invalido!");
        }
    }
}
