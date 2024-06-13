package com.soft.softports.Controller;

import com.soft.softports.Model.*;
import com.soft.softports.Repository.dto.request.TarefaRequestBody;
import com.soft.softports.Repository.dto.response.*;
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
    CasoDeTesteRepository casoDeTesteRepository;

    @GetMapping("/tarefa")
    public Pagina<TarefaResponse> getAllTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        List<TarefaResponse> tarefaResponses = tarefas.stream()
                .map(this::convertToTarefaResponse)
                .toList();
        return paginar(1000, 0, tarefaResponses);
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

    @PutMapping("/tarefa/{id}")
    public ResponseEntity<TarefaResponse> updateTarefa(@PathVariable Long id, @RequestBody TarefaRequestBody tarefaRequestBody) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tarefa tarefa = tarefaOptional.get();

        tarefa.setTitulo(tarefaRequestBody.titulo());
        tarefa.setVersaoSO(tarefaRequestBody.versaoSO());
        tarefa.setCaminho(tarefaRequestBody.caminho());
        tarefa.setDataCorrecao(tarefaRequestBody.dataCorrecao());
        tarefa.setPrioridade(tarefaRequestBody.prioridade());
        tarefa.setStatus(tarefaRequestBody.status());
        tarefa.setScreenshots(tarefaRequestBody.screenshots());
        tarefa.setDescricao(tarefaRequestBody.descricao());

        List<Classificacao> listaClassificacoes = new ArrayList<>();
        tarefaRequestBody.classificacoes().forEach(item -> {
            Classificacao classificacao = classificacaoRepository.findById(item).orElse(null);
            if (classificacao != null) {
                listaClassificacoes.add(classificacao);
            }
        });
        tarefa.setClassificacoes(listaClassificacoes);

        List<Usuario> listaResponsaveis = new ArrayList<>();
        tarefaRequestBody.responsaveis().forEach(item -> {
            Usuario usuario = usuarioRepository.findById(item).orElse(null);
            if (usuario != null) {
                listaResponsaveis.add(usuario);
            }
        });
        tarefa.setResponsaveis(listaResponsaveis);

        List<Historico> listaHistoricos = new ArrayList<>();
        tarefaRequestBody.historicos().forEach(item -> {
            Historico historico = historicoRepository.findById(item).orElse(null);
            if (historico != null) {
                listaHistoricos.add(historico);
            }
        });
        tarefa.setHistorico(listaHistoricos);

        List<CasoDeTeste> listaCasosDeTestes = new ArrayList<>();
        tarefaRequestBody.casoDeTeste().forEach(item -> {
            CasoDeTeste casoDeTeste = casoDeTesteRepository.findById(item).orElse(null);
            if (casoDeTeste != null) {
                listaCasosDeTestes.add(casoDeTeste);
            }
        });
        tarefa.setCasosDeTestes(listaCasosDeTestes);

        Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);

        TarefaResponse tarefaResponse = convertToTarefaResponse(tarefaAtualizada);
        return ResponseEntity.ok(tarefaResponse);
    }

    @DeleteMapping("/tarefa/{id}")
    public void deleteTarefa(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
    }

    private TarefaResponse convertToTarefaResponse(Tarefa tarefa) {
        List<ClassificacaoResponse> classificacaoResponseList = new ArrayList<>();
        tarefa.getClassificacoes().forEach(item -> {
            ClassificacaoResponse classificacaoResponse = new ClassificacaoResponse(
                    item.getClassificacaoId(),
                    item.getNome()
            );
            classificacaoResponseList.add(classificacaoResponse);
        });
        List<UsuarioResponse> usuarioResponseList = new ArrayList<>();
        tarefa.getResponsaveis().forEach(item -> {
            UsuarioResponse usuarioResponse = new UsuarioResponse(
                    item.getUsuarioId(),
                    item.getNome(),
                    item.getCargo()
            );
            usuarioResponseList.add(usuarioResponse);
        });
        List<HistoricoResponse> historicoResponseList = new ArrayList<>();
        tarefa.getHistorico().forEach(item -> {
            HistoricoResponse historicoResponse = new HistoricoResponse(
                    item.getHistoricoId(),
                    item.getOcorrencia(),
                    item.getDataCriacao(),
                    item.getTarefa().getId()
            );
            historicoResponseList.add(historicoResponse);
        });
        List<CasoDeTesteResponse> casoDeTesteResponses = new ArrayList<>();
        tarefa.getCasosDeTestes().forEach(item -> {
            CasoDeTesteResponse casoDeTesteResponse = new CasoDeTesteResponse(
                    item.getCasoDeTesteId(),
                    item.getStatus(),
                    item.getResumo(),
                    item.getPreCondicao(),
                    item.getPassos(),
                    item.getResultadoEsperado(),
                    item.getObservacoes()
            );
            casoDeTesteResponses.add(casoDeTesteResponse);
        });
        return new TarefaResponse(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getVersaoSO(),
                tarefa.getCaminho(),
                tarefa.getDataCorrecao(),
                tarefa.getPrioridade(),
                tarefa.getStatus(),
                tarefa.getScreenshots(),
                tarefa.getDescricao(),
                classificacaoResponseList,
                usuarioResponseList,
                historicoResponseList,
                casoDeTesteResponses
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
        List<CasoDeTeste> listaCasosDeTestes = new ArrayList<>();
        tarefaRequestBody.casoDeTeste().forEach(item -> {
            CasoDeTeste casoDeTeste = casoDeTesteRepository.findById(item).stream().toList().get(0);
            listaCasosDeTestes.add(casoDeTeste);
        });
        Tarefa tarefa = new Tarefa(
                null,
                tarefaRequestBody.titulo(),
                tarefaRequestBody.nome(),
                tarefaRequestBody.versaoSO(),
                tarefaRequestBody.caminho(),
                tarefaRequestBody.dataCorrecao(),
                tarefaRequestBody.prioridade(),
                tarefaRequestBody.status(),
                tarefaRequestBody.screenshots(),
                tarefaRequestBody.descricao(),
                listaClassificacoes,
                listaResponsaveis,
                listaHistoricos,
                listaCasosDeTestes
        );
        tarefaRepository.save(tarefa);
        return new TarefaResponse(
                null,
                tarefaRequestBody.titulo(),
                tarefaRequestBody.versaoSO(),
                tarefaRequestBody.caminho(),
                tarefaRequestBody.dataCorrecao(),
                tarefaRequestBody.prioridade(),
                tarefaRequestBody.status(),
                tarefaRequestBody.screenshots(),
                tarefaRequestBody.descricao(),
                null,
                null,
                null,
                null
        );
    }
}
