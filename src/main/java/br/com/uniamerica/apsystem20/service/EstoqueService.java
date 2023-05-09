package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public void validarEstoque(final Estoque estoque){

        Assert.isTrue(estoque.getNomeEstoque() != null,
                "Nome do estoque nao informado");

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrar(final Estoque estoque){
        this.validarEstoque(estoque);
        this.estoqueRepository.save(estoque);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(final Long id, final Estoque estoque){

        if(id.equals(estoque.getId()) && !this.estoqueRepository.findById(id).isEmpty()){
            this.validarEstoque(estoque);
            this.estoqueRepository.save(estoque);
        }
        else{
            throw new RuntimeException("Id nao encontrado.");
        }

    }
}
