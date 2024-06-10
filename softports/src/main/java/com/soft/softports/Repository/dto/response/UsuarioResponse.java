package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioResponse(

        Long usuarioId,

        String nome,

        String cargo
) { }


