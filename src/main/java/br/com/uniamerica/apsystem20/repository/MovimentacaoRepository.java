package br.com.uniamerica.apsystem20.repository;

import br.com.uniamerica.apsystem20.entity.Movimentacao;
import br.com.uniamerica.apsystem20.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT m FROM Movimentacao m WHERE m.ativo = :ativo")
    List<Movimentacao> findByAtivo(@Param("ativo") boolean ativo);

    @Query("SELECT m FROM Movimentacao m WHERE m.produto = :produto")
    List<Movimentacao> findByProduto(@Param("produto") Produto produto);

}
