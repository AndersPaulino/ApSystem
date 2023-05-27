package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.entity.Tipo;
import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import br.com.uniamerica.apsystem20.repository.TipoRepository;
import br.com.uniamerica.apsystem20.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/tipo")
public class TipoController {
    private final TipoService tipoService;
    private final TipoRepository tipoRepository;

    private final ProdutoRepository produtoRepository;
    @Autowired
    public TipoController(TipoService tipoService, TipoRepository tipoRepository, ProdutoRepository produtoRepository) {
        this.tipoService = tipoService;
        this.tipoRepository = tipoRepository;
        this.produtoRepository = produtoRepository;
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
        try {
            tipoService.cadastrar(tipo);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Tipo tipo) {
        try {
            tipoService.atualizar(id, tipo);
            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        final Tipo tipo = this.tipoRepository.findById(id).orElse(null);
        if (tipo != null) {
            List<Produto> produtosVinculados = this.produtoRepository.findByTipo(tipo);
            if (produtosVinculados.isEmpty()) {
                this.tipoRepository.delete(tipo);
                return ResponseEntity.ok("Registro deletado com sucesso!");
            } else {
                return ResponseEntity.badRequest().body("Não é possível deletar o tipo, pois existem produtos vinculados a ele.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
