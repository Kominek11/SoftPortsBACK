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
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long tarefaId;

    @Column
    String nome;

    @Column
    String descricao;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tarefa")
    List<Historico> historico;


    @ManyToOne
    @JoinColumn(name = "quadro_id")
    @JsonIgnoreProperties("tarefa")
    Quadro quadro;
}
