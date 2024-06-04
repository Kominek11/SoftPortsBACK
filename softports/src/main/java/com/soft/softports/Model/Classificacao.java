package com.soft.softports.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Classificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long classificacaoId;

    @Column
    String nome;

    @ManyToMany(mappedBy = "classificacoes")
    @JsonIgnoreProperties("classificacoes")
    List<Tarefa> tarefas;
}
