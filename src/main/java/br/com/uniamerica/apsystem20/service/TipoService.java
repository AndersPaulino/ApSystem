package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.entity.Tipo;
import br.com.uniamerica.apsystem20.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    public void validarTipo(final Tipo tipo){

        if (!tipo.getNomeTipo().matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Nome do Tipo de Produto inválido");
        }
        if (tipo.getNomeTipo() == null) {
            throw new IllegalArgumentException("Nome do Tipo de Produto não informado");
        }
    }

    public Optional<Tipo> findById(Long id) {
        return tipoRepository.findById(id);
    }

    public List<Tipo> findByNomeTipo(String nomeTipo) {
        return tipoRepository.findByNomeTipo(nomeTipo);
    }

    public List<Tipo> findByAtivo(boolean ativo) {
        return tipoRepository.findByAtivo(ativo);
    }

    public List<Tipo> findAll() {
        return tipoRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrar(Tipo tipo){
        validarTipo(tipo);
        tipoRepository.save(tipo);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(final Long id, final Tipo tipo){
        validarTipo(tipo);

        Optional<Tipo> tipoExistente = tipoRepository.findById(id);

        if (tipoExistente.isPresent()) {
            Tipo tipoAtualizado = tipoExistente.get();

            tipoRepository.save(tipoAtualizado);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }
}
