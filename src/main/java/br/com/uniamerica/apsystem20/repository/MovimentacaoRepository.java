package br.com.uniamerica.apsystem20.repository;

import br.com.uniamerica.apsystem20.entity.Movimentacao;
import br.com.uniamerica.apsystem20.entity.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository {

    @Query("From Movimentacoes where ativo = :ativo")
    List<Movimentacao> findByAtivo(@Param("ativo")final boolean ativo);

    @Query("From Movimentacao where produto = :produto")
    List<Movimentacao> findByProduto(@Param("produto")Produto produto);
}
