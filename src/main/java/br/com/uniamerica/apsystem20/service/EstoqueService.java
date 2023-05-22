package br.com.uniamerica.apsystem20.service;

import br.com.uniamerica.apsystem20.entity.Estoque;
import br.com.uniamerica.apsystem20.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public void validarEstoque(final Estoque estoque) {
        if (estoque.getNomeEstoque() == null || estoque.getNomeEstoque().isEmpty()){
            throw new IllegalArgumentException("Nome do Estoque não informado");
        }
        if (!estoque.getNomeEstoque().matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Nome do Estoque inválido");
        }
    }

    public Optional<Estoque> findById(Long id) {
        return estoqueRepository.findById(id);
    }

    public List<Estoque> findByNomeEstoque(String nomeEstoque) {
        return estoqueRepository.findByNomeEstoque(nomeEstoque);
    }

    public List<Estoque> findByAtivo(boolean ativo) {
        return estoqueRepository.findByAtivo(ativo);
    }

    public List<Estoque> findAll() {
        return estoqueRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Estoque estoque) {
        validarEstoque(estoque);
        estoqueRepository.save(estoque);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(Long id, Estoque estoque) {
        validarEstoque(estoque);

        Optional<Estoque> estoqueExistente = estoqueRepository.findById(id);

        if (estoqueExistente.isPresent()) {
            Estoque estoqueAtualizado = estoqueExistente.get();

            if (estoque.getNomeEstoque() != null) {
                estoqueAtualizado.setNomeEstoque(estoque.getNomeEstoque());
            }

            if (estoque.getDescricaoEstoque() != null) {
                estoqueAtualizado.setDescricaoEstoque(estoque.getDescricaoEstoque());
            }

            if (estoque.getProdutoList() != null) {
                estoqueAtualizado.setProdutoList(estoque.getProdutoList());
            }

            estoqueRepository.save(estoqueAtualizado);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }

}

