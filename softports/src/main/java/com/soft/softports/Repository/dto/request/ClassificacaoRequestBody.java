package com.soft.softports.Repository.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClassificacaoRequestBody(

        String nome,

        List<Long> tarefas
) { }


