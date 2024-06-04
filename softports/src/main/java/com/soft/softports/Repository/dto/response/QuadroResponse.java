package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuadroResponse(

        Long quadroId,

        String titulo,

        Long projetoId
) { }


