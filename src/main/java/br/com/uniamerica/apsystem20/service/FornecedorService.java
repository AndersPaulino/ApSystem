package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Fornecedor;
import br.com.uniamerica.apsystem20.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;
    public void validarFornecedor(final Fornecedor fornecedor) {
        Assert.notNull(fornecedor.getNomeFornecedor(), "Nome do fornecedor não informado");
        Assert.notNull(fornecedor.getTelefoneFornecedor(), "Telefone do fornecedor não informado");

        if (fornecedor.getEmail().isEmpty()) {
            throw new IllegalArgumentException("E-mail do fornecedor não informado");
        }

        if (!fornecedor.getEmail().contains("@")) {
            throw new IllegalArgumentException("E-mail do fornecedor inválido");
        }
    }
    public Optional<Fornecedor> findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public List<Fornecedor> findByNomeFornecedor(String nomeFornecedor) {
        return fornecedorRepository.findByNomeFornecedor(nomeFornecedor);
    }

    public List<Fornecedor> findByAtivo(boolean ativo) {
        return fornecedorRepository.findByAtivo(ativo);
    }

    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void cadastrarFornecedor(Fornecedor fornecedor) {
        validarFornecedor(fornecedor);
        fornecedorRepository.save(fornecedor);
    }
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizarFornecedor(Long id, Fornecedor fornecedor) {
        validarFornecedor(fornecedor);
        Optional<Fornecedor> fornecedorExistente = fornecedorRepository.findById(id);

        if (fornecedorExistente.isPresent()) {
            Fornecedor fornecedorAtualizado = fornecedorExistente.get();
            fornecedorAtualizado.setNomeFornecedor(fornecedor.getNomeFornecedor());
            fornecedorAtualizado.setTelefoneFornecedor(fornecedor.getTelefoneFornecedor());
            fornecedorAtualizado.setEmail(fornecedor.getEmail());

            fornecedorRepository.save(fornecedorAtualizado);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }

}
