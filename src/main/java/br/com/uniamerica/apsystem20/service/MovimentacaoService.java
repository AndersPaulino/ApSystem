package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Movimentacao;
import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public Optional<Movimentacao> findById(Long id){
        return movimentacaoRepository.findById(id);
    }

    public List<Movimentacao> findAll() {
        return movimentacaoRepository.findAll();
    }

    public List<Movimentacao> findByAtivo(boolean ativo){
        return movimentacaoRepository.findByAtivo(ativo);
    }

    public List<Movimentacao> findByProduto(Produto produto){
        return movimentacaoRepository.findByProduto(produto);
    }

}
