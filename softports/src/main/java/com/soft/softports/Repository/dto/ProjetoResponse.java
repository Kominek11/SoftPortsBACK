package com.soft.softports.Repository.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Quadro;
import com.soft.softports.Model.Usuario;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjetoResponse(
        Long projetoId,

        String nome,

        String descricao,

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dataInicio,

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dataFim,

        Quadro quadro,

        Usuario usuario
) { }


