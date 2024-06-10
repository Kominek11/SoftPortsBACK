package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Tarefa;
import jakarta.persistence.*;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CasoDeTesteResponse(

        Long casoDeTesteId,


        String status,


        String resumo,


        String preCondicao,


        String passos,


        String resultadoEsperado,


        String observacoes
) { }





