package com.soft.softports.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long historicoId;

    @Column
    String ocorrencia;

    @Column
    LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    @JsonIgnoreProperties("historico")
    Tarefa tarefa;
}
