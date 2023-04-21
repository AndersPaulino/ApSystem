package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorRepository fornecedorRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.fornecedorRepository.findById(id));
    }

    @GetMapping("/{nomeFornecedor}")
    public ResponseEntity<?> findByNomeFornecedor(@PathVariable String nomeFornecedor){
        return ResponseEntity.ok().body(this.fornecedorRepository.findByNomeFornecedor(nomeFornecedor));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        return ResponseEntity.ok().body(this.fornecedorRepository.findByAtivo(ativo));
    }
}
