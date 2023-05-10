package br.com.uniamerica.apsystem20.repository;

import br.com.uniamerica.apsystem20.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {
    @Query(value = "From Tipo where nomeTipo = :nomeTipo")
    List<Tipo> findByNomeTipo(@Param("nomeTipo") final String nomeTipo);

    @Query(value = "From Tipo where ativo = :ativo")
    List<Tipo> findByAtivo(@Param("ativo") final boolean ativo);
}
