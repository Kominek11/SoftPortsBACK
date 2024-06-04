package com.soft.softports.Repository.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.soft.softports.Model.Projeto;
import com.soft.softports.Model.Tarefa;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuadroRequestBody(

        String titulo,

        Long projetoId
) { }


