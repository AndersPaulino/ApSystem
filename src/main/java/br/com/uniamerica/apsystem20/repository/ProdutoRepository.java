package br.com.uniamerica.apsystem20.repository;

import br.com.uniamerica.apsystem20.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(value = "From Produto where nomeProduto = :nomeProduto",nativeQuery = true)
    List<Produto> findByNomeProduto(@Param("nomeProduto") String nomeProduto);

    @Query(value = "From Produto where ativo = :ativo" ,nativeQuery = true)
    List<Produto> findByAtivo(@Param("ativo") final boolean ativo);

    @Query(value = "From Produto where codigoProduto = :codigoProduto",nativeQuery = true)
    List<Produto> findByCodigoProduto(@Param("codigoProduto") String codigoProduto);

    /*@Query(value = "From Produto produto where produto.tipoProdutoId = :tipoProdutoId",nativeQuery = true)
    List<Produto> findByTipoProdutoId(@Param("tipoProdutoID") Long tipoProdutoId);*/

}

/*DML E DDL*/