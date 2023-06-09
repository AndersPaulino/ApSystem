package br.com.uniamerica.apsystem20.repository;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.entity.Produto;
import br.com.uniamerica.apsystem20.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(value = "From Produto where nomeProduto = :nomeProduto")
    List<Produto> findByNomeProduto(@Param("nomeProduto") String nomeProduto);

    @Query(value = "From Produto where ativo = :ativo")
    List<Produto> findByAtivo(@Param("ativo") final boolean ativo);

    @Query(value = "From Produto where codigoProduto = :codigoProduto")
    List<Produto> findByCodigoProduto(@Param("codigoProduto") String codigoProduto);

    @Query(value = "FROM Produto produto WHERE produto.tipo.id = :tipoId")
    List<Produto> findByTipoProduto(@Param("tipoId") Long tipoId);

    @Query(value = "From Produto where estoque = :estoque")
    List<Produto> findByEstoque(@Param("estoque")Estoque estoque);

    @Query(value = "From Produto where tipo = :tipo")
    List<Produto> findByTipo(@Param("tipo") Tipo tipo);

    @Query(value = "From Produto where fornecedor = :fornecedor")
    List<Produto> findByFornecedor(@Param("fornecedor")Fornecedor fornecedor);

}

/*DML E DDL*/