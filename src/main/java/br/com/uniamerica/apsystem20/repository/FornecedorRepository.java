package br.com.uniamerica.apsystem20.repository;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    @Query(value = "From Fornecedor where nomeFornecedor = :nomeFornecedor" ,nativeQuery = true)
    List<Fornecedor> findByNomeFornecedor(@Param("nomeFornecedor") final String nomeFornecedor);

    @Query(value = "From Fornecedor where ativo = :ativo",nativeQuery = true)
    List<Fornecedor> findByAtivo(@Param("ativo") final boolean ativo);
}
