package com.soft.softports.Repository.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CasoDeTesteRequestBody(

        String status,


        String resumo,


        String preCondicao,


        String passos,


        String resultadoEsperado,


        String observacoes,

        Long tarefaId
) { }


