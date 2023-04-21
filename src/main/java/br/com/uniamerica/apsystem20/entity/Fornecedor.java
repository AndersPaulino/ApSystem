package br.com.uniamerica.apsystem20.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Fornecedores", schema = "public")
public class Fornecedor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nomeFornecedor", unique = true, length = 50)
    private String nomeFornecedor;

    @Getter @Setter
    @Column(name = "telefoneFornecedor", length = 17)
    private String telefoneFornecedor;

    @Getter @Setter
    @Column(name = "emailFornecedor", length = 50)
    private String email;
}
