package com.soft.softports.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long projetoId;

    @Column
    String nome;

    @Column
    String descricao;

    @Column
    LocalDateTime dataInicio;

    @Column
    LocalDateTime dataFim;

    @OneToMany(mappedBy = "projeto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("projeto")
    List<Quadro> quadro;

    @OneToMany(mappedBy = "projeto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("projeto")
    List<Usuario> usuario;

    public Projeto(String nome, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
