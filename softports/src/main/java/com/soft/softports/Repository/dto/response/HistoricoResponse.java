package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Tarefa;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HistoricoResponse(

        Long historicoId,

        String ocorrencia,

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dataCriacao
) { }


