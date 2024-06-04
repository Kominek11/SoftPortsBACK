package com.soft.softports.Controller;

import com.soft.softports.Model.*;
import com.soft.softports.Repository.dto.request.TarefaRequestBody;
import com.soft.softports.Repository.dto.response.Pagina;
import com.soft.softports.Repository.dto.response.TarefaResponse;
import com.soft.softports.Repository.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TarefaController {

    TarefaRepository tarefaRepository;
    QuadroRepository quadroRepository;
    ClassificacaoRepository classificacaoRepository;
    HistoricoRepository historicoRepository;
    UsuarioRepository usuarioRepository;

    @GetMapping("/tarefa")
    public Pagina<TarefaResponse> getAllTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        List<TarefaResponse> tarefaResponses = tarefas.stream()
                .map(this::convertToTarefaResponse)
                .toList();
        return paginar(2, 0, tarefaResponses);
    }

    @GetMapping("/tarefa/{id}")
    public ResponseEntity<TarefaResponse> getTarefaById(@PathVariable Long id) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        return tarefaOptional.map(tarefa -> ResponseEntity.ok(convertToTarefaResponse(tarefa)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tarefa")
    public TarefaResponse saveTarefa(@RequestBody TarefaRequestBody tarefaRequestBody) {
        return criarTarefa(tarefaRequestBody);
    }

    @DeleteMapping("/tarefa/{id}")
    public void deleteTarefa(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
    }

    private TarefaResponse convertToTarefaResponse(Tarefa tarefa) {
        return new TarefaResponse(
                tarefa.getTarefaId(),
                tarefa.getNome(),
                tarefa.getTitulo(),
                tarefa.getVersaoSO(),
                tarefa.getCaminho(),
                tarefa.getDataCorrecao(),
                tarefa.getPrioridade(),
                tarefa.getStatus(),
                tarefa.getScreenshot(),
                tarefa.getDescricao(),
                tarefa.getQuadro().getQuadroId(),
                tarefa.getClassificacoes(),
                tarefa.getResponsaveis(),
                tarefa.getHistorico()
        );
    }

    private Pagina<TarefaResponse> paginar(Integer tamanhoPagina,
                                           Integer numeroPagina,
                                           List<TarefaResponse> infoTarefas) {
        int totalRegistros = infoTarefas.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);

        int fromIndex = numeroPagina * tamanhoPagina;
        int toIndex = Math.min(fromIndex + tamanhoPagina, totalRegistros);

        List<TarefaResponse> conteudo;
        if (fromIndex > totalRegistros) {
            conteudo = List.of();
        } else {
            conteudo = infoTarefas.subList(fromIndex, toIndex);
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

    private TarefaResponse criarTarefa(TarefaRequestBody tarefaRequestBody) {
        Quadro quadro = quadroRepository.findById(tarefaRequestBody.quadroId()).stream().toList().get(0);
        List<Classificacao> listaClassificacoes = new ArrayList<>();
        tarefaRequestBody.classificacoes().forEach(item -> {
            Classificacao classificacao = classificacaoRepository.findById(item).stream().toList().get(0);
            listaClassificacoes.add(classificacao);
        });
        List<Usuario> listaResponsaveis = new ArrayList<>();
        tarefaRequestBody.responsaveis().forEach(item -> {
            Usuario usuario = usuarioRepository.findById(item).stream().toList().get(0);
            listaResponsaveis.add(usuario);
        });
        List<Historico> listaHistoricos = new ArrayList<>();
        tarefaRequestBody.historicos().forEach(item -> {
            Historico historico = historicoRepository.findById(item).stream().toList().get(0);
            listaHistoricos.add(historico);
        });
        Tarefa tarefa = new Tarefa(
                null,
                tarefaRequestBody.nome(),
                tarefaRequestBody.titulo(),
                tarefaRequestBody.versaoSO(),
                tarefaRequestBody.caminho(),
                tarefaRequestBody.dataCorrecao(),
                tarefaRequestBody.prioridade(),
                tarefaRequestBody.status(),
                tarefaRequestBody.screenshot(),
                tarefaRequestBody.descricao(),
                listaClassificacoes,
                listaResponsaveis,
                listaHistoricos,
                quadro
        );
        tarefaRepository.save(tarefa);
        return new TarefaResponse(
                null,
                tarefaRequestBody.nome(),
                tarefaRequestBody.titulo(),
                tarefaRequestBody.versaoSO(),
                tarefaRequestBody.caminho(),
                tarefaRequestBody.dataCorrecao(),
                tarefaRequestBody.prioridade(),
                tarefaRequestBody.status(),
                tarefaRequestBody.screenshot(),
                tarefaRequestBody.descricao(),
                quadro.getQuadroId(),
                listaClassificacoes,
                listaResponsaveis,
                listaHistoricos
        );
    }
}
