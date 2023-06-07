package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.entity.Movimentacao;
import br.com.uniamerica.apsystem20.repository.MovimentacaoRepository;
import br.com.uniamerica.apsystem20.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    private MovimentacaoService movimentacaoService;

    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public MovimentacaoController(MovimentacaoService movimentacaoService, MovimentacaoRepository movimentacaoRepository){
        this.movimentacaoService = movimentacaoService;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimentacao> findById(@PathVariable Long id) {
        Optional<Movimentacao> movimentacao = movimentacaoService.findById(id);
        if (movimentacao.isPresent()) {
            return ResponseEntity.ok().body(movimentacao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo) {
        List<Movimentacao> movimentacao = movimentacaoService.findByAtivo(ativo);
        if (movimentacao.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(movimentacao);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Movimentacao> movimentacao = movimentacaoService.findAll();
        if (movimentacao.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(movimentacao);
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Movimentacao movimentacao) {
        try {
            movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
