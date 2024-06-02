package com.soft.softports.Controller;

import com.querydsl.core.BooleanBuilder;
import com.soft.softports.Model.Projeto;
import com.soft.softports.Repository.dto.Pagina;
import com.soft.softports.Repository.dto.ProjetoResponse;
import com.soft.softports.Repository.interfaces.ProjetoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ProjetoController {

    ProjetoRepository projetoRepository;

    @GetMapping("/projeto")
    public Pagina<ProjetoResponse> getAllProjetos() {
        List<Projeto> projetos = projetoRepository.findAll();
        List<ProjetoResponse> projetoResponses = projetos.stream()
                .map(this::convertToProjetoResponse)
                .toList();
        return paginar(2, 0, projetoResponses);
    }

    @GetMapping("/projeto/{id}")
    public ResponseEntity<ProjetoResponse> getProjetoById(@PathVariable Long id) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(id);
        return projetoOptional.map(projeto -> ResponseEntity.ok(convertToProjetoResponse(projeto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/projeto")
    public Projeto saveProjeto(@RequestBody Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    @DeleteMapping("/projeto/{id}")
    public void deleteProjeto(@PathVariable Long id) {
        projetoRepository.deleteById(id);
    }

    private ProjetoResponse convertToProjetoResponse(Projeto projeto) {
        return new ProjetoResponse(
                projeto.getProjetoId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getQuadro()
        );
    }

    private Pagina<ProjetoResponse> paginar(Integer tamanhoPagina,
                                                 Integer numeroPagina,
                                                 List<ProjetoResponse> infoProjetos) {
        int totalRegistros = infoProjetos.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);

        int fromIndex = numeroPagina * tamanhoPagina;
        int toIndex = Math.min(fromIndex + tamanhoPagina, totalRegistros);

        List<ProjetoResponse> conteudo;
        if (fromIndex > totalRegistros) {
            conteudo = List.of();
        } else {
            conteudo = infoProjetos.subList(fromIndex, toIndex);
        }

        return new Pagina<>(
                true,
                numeroPagina,
                totalPaginas,
                tamanhoPagina,
                (long) totalRegistros,
                conteudo
        );

    }
}
