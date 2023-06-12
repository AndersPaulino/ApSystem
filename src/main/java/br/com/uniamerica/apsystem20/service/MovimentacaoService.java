package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Movimentacao;
import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public void validarMovimentacao(final Movimentacao movimentacao) {
        if (movimentacao.getProduto() == null) {
            throw new IllegalArgumentException("Produto não registrado!");
        }

        if (movimentacao.getQuantidade() == null) {
            throw new IllegalArgumentException("Quantidade do Produto não pode estar vazia!");
        }

        if (movimentacao.getSaida() != null) {
            if (movimentacao.getSaida() > movimentacao.getQuantidade()) {
                throw new IllegalArgumentException("Saída de Produtos não pode exceder a quantidade de Produtos!");
            } else {
                movimentacao.setTotalProduto(movimentacao.getQuantidade() - movimentacao.getSaida());
                BigDecimal valorVenda = movimentacao.getValorVenda() != null ? movimentacao.getValorVenda() : BigDecimal.ZERO;
                movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getSaida()).multiply(valorVenda));
            }
        }

        if (movimentacao.getValorVenda() == null) {
            throw new IllegalArgumentException("Valor de venda do Produto não informado!");
        }

        if (movimentacao.getValorCompra() == null) {
            throw new IllegalArgumentException("Valor de compra do Produto não informado!");
        }
    }

    public void validarMovimentacaoExistente(final Movimentacao movimentacao) {
        if (movimentacao.getProduto() != null) {
            throw new IllegalArgumentException("O produto não pode ser alterado!");
        }

        if (movimentacao.getSaida() != null && movimentacao.getSaida() < movimentacao.getQuantidade()) {
            movimentacao.setTotalProduto(movimentacao.getQuantidade() - movimentacao.getSaida());
            BigDecimal valorVenda = movimentacao.getValorVenda() != null ? movimentacao.getValorVenda() : BigDecimal.ZERO;
            movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getSaida()).multiply(valorVenda));
        } else {
            movimentacao.setSaida(null);
            movimentacao.setTotalProduto(movimentacao.getQuantidade());
            movimentacao.setValorTotal(BigDecimal.ZERO);
        }
    }

    public Optional<Movimentacao> findById(Long id) {
        return movimentacaoRepository.findById(id);
    }

    public List<Movimentacao> findAll() {
        return movimentacaoRepository.findAll();
    }

    public List<Movimentacao> findByAtivo(boolean ativo) {
        return movimentacaoRepository.findByAtivo(ativo);
    }

    public List<Movimentacao> findByProduto(Produto produto) {
        return movimentacaoRepository.findByProduto(produto);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrar(final Movimentacao movimentacao) {
        validarMovimentacao(movimentacao);
        movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(final Long id, final Movimentacao movimentacao) {
        validarMovimentacaoExistente(movimentacao);
        Optional<Movimentacao> optionalMovimentacao = movimentacaoRepository.findById(id);

        if (optionalMovimentacao.isPresent()) {
            Movimentacao movimentacaoExistente = optionalMovimentacao.get();
            movimentacaoExistente.setQuantidade(movimentacao.getQuantidade());
            movimentacaoExistente.setValorVenda(movimentacao.getValorVenda());
            movimentacaoExistente.setValorCompra(movimentacao.getValorCompra());
            movimentacaoExistente.setAtivo(movimentacao.isAtivo());
            movimentacaoExistente.setSaida(movimentacao.getSaida());
            movimentacaoRepository.save(movimentacaoExistente);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }

}
