package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Tarefa;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClassificacaoResponse(

        Long classificacaoId,

        String nome,

        List<Tarefa> tarefas
) { }


