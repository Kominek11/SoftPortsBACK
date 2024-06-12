package com.soft.softports.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CasoDeTeste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long casoDeTesteId;

    @Column
    String status;

    @Column
    String resumo;

    @Column
    String preCondicao;

    @Column
    String passos;

    @Column
    String resultadoEsperado;

    @Column
    String observacoes;

    @ManyToMany(mappedBy = "casosDeTestes")
    @JsonIgnoreProperties("casosDeTestes")
    List<Tarefa> tarefas;
}
