package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public void validarEstoque(final Estoque estoque) {
        Assert.isTrue(estoque.getNomeEstoque() != null, "Nome do estoque não informado");
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Estoque estoque) {
        validarEstoque(estoque);
        estoqueRepository.save(estoque);
    }

    @Transactional(rollbackFor = Exception.class)
    public void atualizar(final Long id, final Estoque estoque) {
        Optional<Estoque> estoqueExistente = estoqueRepository.findById(id);

        if (estoqueExistente.isPresent() && id.equals(estoque.getId())) {
            validarEstoque(estoque);
            estoqueRepository.save(estoque);
        } else {
            throw new RuntimeException("ID não encontrado");
        }
    }
}

