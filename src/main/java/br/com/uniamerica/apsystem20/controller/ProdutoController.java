package br.com.uniamerica.apsystem20.controller;

import br.com.uniamerica.apsystem20.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/produto")
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.produtoRepository.findById(id));
    }

    @GetMapping("/nome/{nomeProduto}")
    public ResponseEntity<?> findByNomeProduto(@PathVariable String nomeProduto){
        return ResponseEntity.ok().body(this.produtoRepository.findByNomeProduto(nomeProduto));
    }

    @GetMapping("/{ativo}")
    public ResponseEntity<?> findByAtivo(@PathVariable boolean ativo){
        return ResponseEntity.ok().body(this.produtoRepository.findByAtivo(ativo));
    }
    @GetMapping("/codigo/{codigoProduto}")
    public ResponseEntity<?> findByCodigoProduto(@PathVariable String codigoProduto){
        return ResponseEntity.ok().body(this.produtoRepository.findByCodigoProduto(codigoProduto));
    }

   /* @GetMapping("/Tipo/{tipoProdutoId}")
    public ResponseEntity<?> findByTipoProdutoId(@PathVariable Long tipoProdutoId){
        return ResponseEntity.ok().body(this.produtoRepository.findByTipoProdutoId(tipoProdutoId));
    }*/
}
