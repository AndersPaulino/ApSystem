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
        if (fornecedor.getEmail().isEmpty()) {
            throw new IllegalArgumentException("E-mail do fornecedor não informado");
        }
        if (!fornecedor.getEmail().contains("@")) {
            throw new IllegalArgumentException("E-mail do fornecedor inválido");
        }
        if (fornecedor.getTelefoneFornecedor().isEmpty()){
            throw new IllegalArgumentException("Telefone do fornecedor não informado");
        }
        if (!fornecedor.getTelefoneFornecedor().matches("\\d+")) {
            throw new IllegalArgumentException("Telefone do fornecedor inválido");
        }
        if (fornecedor.getNomeFornecedor().isEmpty()){
            throw new IllegalArgumentException("Nome do fornecedor não informado");
        }
        if (!fornecedor.getNomeFornecedor().matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Nome do fornecedor inválido");
        }
        if (fornecedor.getNomeFornecedor() == null && fornecedor.getTelefoneFornecedor() == null && fornecedor.getEmail() == null) {
            throw new IllegalArgumentException("Nenhum campo do fornecedor informado");
        }
    }

    public void validarFornecedorCadastrado(final Fornecedor fornecedor) {
        String telefone = fornecedor.getTelefoneFornecedor();
        String nome = fornecedor.getNomeFornecedor();

        if (nome != null) {
            // Verificar se o nome contém apenas letras maiúsculas, minúsculas e espaço
            if (!nome.matches("[a-zA-Z ]+")) {
                throw new IllegalArgumentException("Nome do fornecedor inválido");
            }
        }

        if (telefone != null) {
            // Verificar se o telefone contém apenas números
            if (!telefone.matches("\\d+")) {
                throw new IllegalArgumentException("Telefone do fornecedor inválido");
            }
        }

        if (fornecedor.getEmail() != null) {
            if (!fornecedor.getEmail().contains("@")) {
                throw new IllegalArgumentException("E-mail do fornecedor inválido");
            }
        }

        if (nome == null && telefone == null && fornecedor.getEmail() == null) {
            throw new IllegalArgumentException("Nenhum campo do fornecedor informado");
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
        validarFornecedorCadastrado(fornecedor);

        Optional<Fornecedor> fornecedorExistente = fornecedorRepository.findById(id);

        if (fornecedorExistente.isPresent()) {
            Fornecedor fornecedorAtualizado = fornecedorExistente.get();

            if (fornecedor.getNomeFornecedor() != null) {
                fornecedorAtualizado.setNomeFornecedor(fornecedor.getNomeFornecedor());
            }

            if (fornecedor.getTelefoneFornecedor() != null) {
                fornecedorAtualizado.setTelefoneFornecedor(fornecedor.getTelefoneFornecedor());
            }

            if (fornecedor.getEmail() != null) {
                fornecedorAtualizado.setEmail(fornecedor.getEmail());
            }

            fornecedorRepository.save(fornecedorAtualizado);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }
}
