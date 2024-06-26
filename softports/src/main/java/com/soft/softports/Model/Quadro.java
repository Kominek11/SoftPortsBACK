package com.soft.softports.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Quadro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long quadroId;

    @Column
    String titulo;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    @JsonIgnore
    Projeto projeto;


    public Quadro(String titulo, Projeto projeto) {
        this.titulo = titulo;
        this.projeto = projeto;
    }
}
