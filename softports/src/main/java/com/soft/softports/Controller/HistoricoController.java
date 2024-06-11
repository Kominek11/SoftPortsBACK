package com.soft.softports.Controller;

import com.soft.softports.Model.Projeto;
import com.soft.softports.Model.Historico;
import com.soft.softports.Model.Tarefa;
import com.soft.softports.Repository.dto.request.HistoricoRequestBody;
import com.soft.softports.Repository.dto.response.Pagina;
import com.soft.softports.Repository.dto.response.HistoricoResponse;
import com.soft.softports.Repository.interfaces.ProjetoRepository;
import com.soft.softports.Repository.interfaces.HistoricoRepository;
import com.soft.softports.Repository.interfaces.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class HistoricoController {

    HistoricoRepository historicoRepository;
    TarefaRepository tarefaRepository;

    @GetMapping("/historico")
    public Pagina<HistoricoResponse> getAllHistoricos() {
        List<Historico> historicos = historicoRepository.findAll();
        List<HistoricoResponse> historicoResponses = historicos.stream()
                .map(this::convertToHistoricoResponse)
                .toList();
        return paginar(2, 0, historicoResponses);
    }

    @GetMapping("/historico/{id}")
    public ResponseEntity<HistoricoResponse> getHistoricoById(@PathVariable Long id) {
        Optional<Historico> historicoOptional = historicoRepository.findById(id);
        return historicoOptional.map(historico -> ResponseEntity.ok(convertToHistoricoResponse(historico)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/historico")
    public HistoricoResponse saveHistorico(@RequestBody HistoricoRequestBody historicoRequestBody) {
        return criarHistorico(historicoRequestBody);
    }

    @DeleteMapping("/historico/{id}")
    public void deleteHistorico(@PathVariable Long id) {
        historicoRepository.deleteById(id);
    }

    private HistoricoResponse convertToHistoricoResponse(Historico historico) {
        return new HistoricoResponse(
                historico.getHistoricoId(),
                historico.getOcorrencia(),
                historico.getDataCriacao(),
                historico.getTarefa().getId()
        );
    }

    private Pagina<HistoricoResponse> paginar(Integer tamanhoPagina,
                                           Integer numeroPagina,
                                           List<HistoricoResponse> infoHistoricos) {
        int totalRegistros = infoHistoricos.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);

        int fromIndex = numeroPagina * tamanhoPagina;
        int toIndex = Math.min(fromIndex + tamanhoPagina, totalRegistros);

        List<HistoricoResponse> conteudo;
        if (fromIndex > totalRegistros) {
            conteudo = List.of();
        } else {
            conteudo = infoHistoricos.subList(fromIndex, toIndex);
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

    private HistoricoResponse criarHistorico(HistoricoRequestBody historicoRequestBody) {
        Tarefa tarefa = tarefaRepository.findById(historicoRequestBody.tarefaId()).stream().toList().get(0);
        Historico historico = new Historico(
                null,
                historicoRequestBody.ocorrencia(),
                historicoRequestBody.dataCriacao(),
                tarefa
        );
        historicoRepository.save(historico);
        return new HistoricoResponse(
                null,
                historicoRequestBody.ocorrencia(),
                historicoRequestBody.dataCriacao(),
                tarefa.getId()
        );
    }
}
