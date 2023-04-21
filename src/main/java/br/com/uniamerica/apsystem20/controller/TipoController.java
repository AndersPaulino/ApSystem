package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/tipo")
public class TipoController {
    @Autowired
    TipoRepository tipoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.tipoRepository.findById(id));
    }

    @GetMapping("/{nomeTipo}")
    public ResponseEntity<?> findByNomeTipo(@PathVariable String nomeTipo){
        return ResponseEntity.ok().body(this.tipoRepository.findByNomeTipo(nomeTipo));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        return ResponseEntity.ok().body(this.tipoRepository.findByAtivo(ativo));
    }
}
