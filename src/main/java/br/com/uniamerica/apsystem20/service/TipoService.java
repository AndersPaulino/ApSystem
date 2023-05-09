package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Tipo;
import br.com.uniamerica.apsystem20.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    public void validarTipo(final Tipo tipo){

        Assert.isTrue(tipo.getNomeTipo() != null,
                "Nome do tipo do produto nao informado");

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrar(final Tipo tipo){
        this.validarTipo(tipo);
        this.tipoRepository.save(tipo);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(final Long id, final Tipo tipo){

        if(id.equals(tipo.getId()) && !this.tipoRepository.findById(id).isEmpty()){
            this.validarTipo(tipo);
            this.tipoRepository.save(tipo);
        }
        else{
            throw new RuntimeException("Id nao encontrado.");
        }

    }
}
