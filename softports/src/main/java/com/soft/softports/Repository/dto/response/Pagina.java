package com.soft.softports.Repository.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Pagina<T> {
    boolean paginado;
    Integer numeroPagina;
    Integer totalPaginas;
    Integer tamanhoPagina;
    Long totalRegistros;
    //@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo")
    List<T> conteudo;

    public Pagina(
            boolean paginado,
            Integer numeroPagina,
            Integer totalPaginas,
            Integer tamanhoPagina,
            Long totalRegistros,
            List<T> conteudo) {
        this.paginado = paginado;
        this.numeroPagina = numeroPagina;
        this.totalPaginas = totalPaginas;
        this.tamanhoPagina = tamanhoPagina;
        this.totalRegistros = totalRegistros;
        this.conteudo = conteudo;
    }

    public Pagina(boolean paginado, List<T> conteudo) {
        this.paginado = paginado;
        this.conteudo = conteudo;
    }

    public Pagina() {}
}
