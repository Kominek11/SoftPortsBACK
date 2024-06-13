package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Classificacao;
import com.soft.softports.Model.Historico;
import com.soft.softports.Model.Usuario;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TarefaResponse(

        Long id,

        String titulo,

        String versaoSO,

        String caminho,

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dataCorrecao,

        String prioridade,

        String status,

        List<String> screenshot,

        String descricao,

        List<ClassificacaoResponse> classificacoes,

        List<UsuarioResponse> responsaveis,

        List<CasoDeTesteResponse> casosDeTestes
) { }


