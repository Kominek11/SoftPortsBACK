package com.soft.softports.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String titulo;

    @Column
    String nome;

    @Column
    String versaoSO;

    @Column
    String caminho;

    @Column
    LocalDateTime dataCorrecao;

    @Column
    String prioridade;

    @Column
    String status;

    @Column
    List<String> screenshots;

    @Column
    String descricao;

    @ManyToMany
    @JoinTable(
            name = "tarefa_classificacao",
            joinColumns = @JoinColumn(name = "tarefa_id"),
            inverseJoinColumns = @JoinColumn(name = "classificacao_id")
    )
    List<Classificacao> classificacoes;

    @ManyToMany
    @JoinTable(
            name = "tarefa_usuario",
            joinColumns = @JoinColumn(name = "tarefa_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    List<Usuario> responsaveis;

    @ManyToMany
    @JoinTable(
            name = "tarefa_casoDeTeste",
            joinColumns = @JoinColumn(name = "tarefa_id"),
            inverseJoinColumns = @JoinColumn(name = "casoDeTeste_id")
    )
    @JsonIgnoreProperties("tarefa")
    List<CasoDeTeste> casosDeTestes;
}
