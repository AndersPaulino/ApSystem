package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/estoque")
public class EstoqueController {
    @Autowired
    EstoqueRepository estoqueRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.estoqueRepository.findById(id));
    }

    @GetMapping("/{nomeEstoque}")
    public ResponseEntity<?> findByNomeEstoque(@PathVariable String nomeEstoque){
        return ResponseEntity.ok().body(this.estoqueRepository.findByNomeEstoque(nomeEstoque));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        return ResponseEntity.ok().body(this.estoqueRepository.findByAtivo(ativo));
    }


}
