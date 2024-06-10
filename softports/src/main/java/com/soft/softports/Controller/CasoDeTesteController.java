package com.soft.softports.Controller;

import com.soft.softports.Model.Projeto;
import com.soft.softports.Model.CasoDeTeste;
import com.soft.softports.Model.Tarefa;
import com.soft.softports.Repository.dto.request.CasoDeTesteRequestBody;
import com.soft.softports.Repository.dto.response.Pagina;
import com.soft.softports.Repository.dto.response.CasoDeTesteResponse;
import com.soft.softports.Repository.interfaces.ProjetoRepository;
import com.soft.softports.Repository.interfaces.CasoDeTesteRepository;
import com.soft.softports.Repository.interfaces.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CasoDeTesteController {

    CasoDeTesteRepository casoDeTesteRepository;
    TarefaRepository tarefaRepository;

    @GetMapping("/casoDeTeste")
    public Pagina<CasoDeTesteResponse> getAllCasoDeTestes() {
        List<CasoDeTeste> casoDeTestes = casoDeTesteRepository.findAll();
        List<CasoDeTesteResponse> casoDeTesteResponses = casoDeTestes.stream()
                .map(this::convertToCasoDeTesteResponse)
                .toList();
        return paginar(2, 0, casoDeTesteResponses);
    }

    @GetMapping("/casoDeTeste/{id}")
    public ResponseEntity<CasoDeTesteResponse> getCasoDeTesteById(@PathVariable Long id) {
        Optional<CasoDeTeste> casoDeTesteOptional = casoDeTesteRepository.findById(id);
        return casoDeTesteOptional.map(casoDeTeste -> ResponseEntity.ok(convertToCasoDeTesteResponse(casoDeTeste)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/casoDeTeste")
    public CasoDeTesteResponse saveCasoDeTeste(@RequestBody CasoDeTesteRequestBody casoDeTesteRequestBody) {
        return criarCasoDeTeste(casoDeTesteRequestBody);
    }

    @DeleteMapping("/casoDeTeste/{id}")
    public void deleteCasoDeTeste(@PathVariable Long id) {
        casoDeTesteRepository.deleteById(id);
    }

    private CasoDeTesteResponse convertToCasoDeTesteResponse(CasoDeTeste casoDeTeste) {
        return new CasoDeTesteResponse(
            casoDeTeste.getCasoDeTesteId(),
            casoDeTeste.getStatus(),
            casoDeTeste.getResumo(),
            casoDeTeste.getPreCondicao(),
            casoDeTeste.getPassos(),
            casoDeTeste.getResultadoEsperado(),
            casoDeTeste.getObservacoes()
        );
    }

    private Pagina<CasoDeTesteResponse> paginar(Integer tamanhoPagina,
                                              Integer numeroPagina,
                                              List<CasoDeTesteResponse> infoCasoDeTestes) {
        int totalRegistros = infoCasoDeTestes.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);

        int fromIndex = numeroPagina * tamanhoPagina;
        int toIndex = Math.min(fromIndex + tamanhoPagina, totalRegistros);

        List<CasoDeTesteResponse> conteudo;
        if (fromIndex > totalRegistros) {
            conteudo = List.of();
        } else {
            conteudo = infoCasoDeTestes.subList(fromIndex, toIndex);
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

    private CasoDeTesteResponse criarCasoDeTeste(CasoDeTesteRequestBody casoDeTesteRequestBody) {
        Tarefa tarefa = tarefaRepository.findById(casoDeTesteRequestBody.tarefaId()).stream().toList().get(0);
        CasoDeTeste casoDeTeste = new CasoDeTeste(
                null,
                casoDeTesteRequestBody.status(),
                casoDeTesteRequestBody.resumo(),
                casoDeTesteRequestBody.preCondicao(),
                casoDeTesteRequestBody.passos(),
                casoDeTesteRequestBody.resultadoEsperado(),
                casoDeTesteRequestBody.observacoes(),
                tarefa
        );
        casoDeTesteRepository.save(casoDeTeste);
        return new CasoDeTesteResponse(
                null,
                casoDeTesteRequestBody.status(),
                casoDeTesteRequestBody.resumo(),
                casoDeTesteRequestBody.preCondicao(),
                casoDeTesteRequestBody.passos(),
                casoDeTesteRequestBody.resultadoEsperado(),
                casoDeTesteRequestBody.observacoes()
        );
    }
}
