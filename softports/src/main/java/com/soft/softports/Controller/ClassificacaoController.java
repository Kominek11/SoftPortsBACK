package com.soft.softports.Controller;

import com.soft.softports.Model.*;
import com.soft.softports.Repository.dto.request.ClassificacaoRequestBody;
import com.soft.softports.Repository.dto.response.Pagina;
import com.soft.softports.Repository.dto.response.ClassificacaoResponse;
import com.soft.softports.Repository.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ClassificacaoController {

    ClassificacaoRepository classificacaoRepository;
    TarefaRepository tarefaRepository;

    @GetMapping("/classificacao")
    public Pagina<ClassificacaoResponse> getAllClassificacaos() {
        List<Classificacao> classificacoes = classificacaoRepository.findAll();
        List<ClassificacaoResponse> classificacaoResponses = classificacoes.stream()
                .map(this::convertToClassificacaoResponse)
                .toList();
        return paginar(1000, 0, classificacaoResponses);
    }

    @GetMapping("/classificacao/{id}")
    public ResponseEntity<ClassificacaoResponse> getClassificacaoById(@PathVariable Long id) {
        Optional<Classificacao> classificacaoOptional = classificacaoRepository.findById(id);
        return classificacaoOptional.map(classificacao -> ResponseEntity.ok(convertToClassificacaoResponse(classificacao)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/classificacao")
    public ClassificacaoResponse saveClassificacao(@RequestBody ClassificacaoRequestBody classificacaoRequestBody) {
        return criarClassificacao(classificacaoRequestBody);
    }

    @DeleteMapping("/classificacao/{id}")
    public void deleteClassificacao(@PathVariable Long id) {
        classificacaoRepository.deleteById(id);
    }

    private ClassificacaoResponse convertToClassificacaoResponse(Classificacao classificacao) {
        return new ClassificacaoResponse(
                classificacao.getClassificacaoId(),
                classificacao.getNome()
        );
    }

    private Pagina<ClassificacaoResponse> paginar(Integer tamanhoPagina,
                                           Integer numeroPagina,
                                           List<ClassificacaoResponse> infoClassificacaos) {
        int totalRegistros = infoClassificacaos.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);

        int fromIndex = numeroPagina * tamanhoPagina;
        int toIndex = Math.min(fromIndex + tamanhoPagina, totalRegistros);

        List<ClassificacaoResponse> conteudo;
        if (fromIndex > totalRegistros) {
            conteudo = List.of();
        } else {
            conteudo = infoClassificacaos.subList(fromIndex, toIndex);
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

    private ClassificacaoResponse criarClassificacao(ClassificacaoRequestBody classificacaoRequestBody) {
        List<Tarefa> listaTarefas = new ArrayList<>();
        classificacaoRequestBody.tarefas().forEach(item -> {
            Tarefa tarefa = tarefaRepository.findById(item).stream().toList().get(0);
            listaTarefas.add(tarefa);
        });
        Classificacao classificacao = new Classificacao(
                null,
                classificacaoRequestBody.nome(),
                listaTarefas
        );
        classificacaoRepository.save(classificacao);
        return new ClassificacaoResponse(
                null,
                classificacaoRequestBody.nome()
        );
    }
}
