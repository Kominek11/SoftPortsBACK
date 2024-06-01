package com.soft.softports.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long usuarioId;

    @Column
    String nome;

    @Column
    String cargo;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    @JsonIgnoreProperties("usuario")
    Projeto projeto;
}
