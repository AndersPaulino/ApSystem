package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public void validarFornecedor(final Fornecedor fornecedor){

        Assert.isTrue(fornecedor.getNomeFornecedor() != null,
                "Nome do fornecedor nao informado");

        Assert.isTrue(fornecedor.getTelefoneFornecedor() != null,
                "Telefone do fornecedor não informado");

        Assert.isTrue(fornecedor.getEmail() != null,
                "E-mail do fornecedor não informado");

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrar(final Fornecedor fornecedor){
        this.validarFornecedor(fornecedor);
        this.fornecedorRepository.save(fornecedor);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(final Long id, final Fornecedor fornecedor){

        if(id.equals(fornecedor.getId()) && !this.fornecedorRepository.findById(id).isEmpty()){
            this.validarFornecedor(fornecedor);
            this.fornecedorRepository.save(fornecedor);
        }
        else{
            throw new RuntimeException("Id nao encontrado.");
        }
    }
}
