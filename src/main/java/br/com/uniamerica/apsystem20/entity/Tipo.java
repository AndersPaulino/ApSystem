package br.com.uniamerica.apsystem20.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipos", schema = "public")
public class Tipo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome_tipo", unique = true, length = 100)
    private String nomeTipo;
}
