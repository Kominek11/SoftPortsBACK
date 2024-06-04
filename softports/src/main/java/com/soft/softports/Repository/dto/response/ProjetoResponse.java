package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Quadro;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjetoResponse(

        Long projetoId,

        String nome,

        String descricao,

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dataInicio,

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dataFim
) { }


