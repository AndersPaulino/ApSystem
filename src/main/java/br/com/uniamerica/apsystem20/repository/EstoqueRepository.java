package br.com.uniamerica.apsystem20.repository;

import br.com.uniamerica.apsystem20.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    @Query("From Estoque where nomeEstoque = :nomeEstoque")
    public List<Estoque> findByNomeEstoque(@Param("nomeEstoque")final String nomeEstoque);
    @Query("From Estoque where ativo = :ativo")
    public List<Estoque> findByAtivo(@Param("ativo")final boolean ativo);

}
