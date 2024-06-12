package com.soft.softports.Repository.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Classificacao;
import com.soft.softports.Model.Historico;
import com.soft.softports.Model.Quadro;
import com.soft.softports.Model.Usuario;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TarefaRequestBody(

        String titulo,

        String nome,

        String versaoSO,

        String caminho,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataCorrecao,

        String prioridade,

        String status,

        List<String> screenshots,

        String descricao,

        List<Long> classificacoes,

        List<Long> responsaveis,

        List<Long> historicos,

        List<Long> casoDeTeste
) { }