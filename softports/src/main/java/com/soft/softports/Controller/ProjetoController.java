package com.soft.softports.Controller;

import com.soft.softports.Model.Projeto;
import com.soft.softports.Repository.dto.request.ProjetoRequestBody;
import com.soft.softports.Repository.dto.response.Pagina;
import com.soft.softports.Repository.dto.response.ProjetoResponse;
import com.soft.softports.Repository.interfaces.ProjetoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ProjetoResponse saveProjeto(@RequestBody ProjetoRequestBody projetoRequestBody) {
        return criarProjeto(projetoRequestBody);
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
                projeto.getDataFim()
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

    private ProjetoResponse criarProjeto(ProjetoRequestBody projetoRequestBody) {
        Projeto projeto = new Projeto(
                projetoRequestBody.nome(),
                projetoRequestBody.descricao(),
                projetoRequestBody.dataInicio(),
                projetoRequestBody.dataFim()
        );
        projetoRepository.save(projeto);
        return new ProjetoResponse(
                null,
                projetoRequestBody.nome(),
                projetoRequestBody.descricao(),
                projetoRequestBody.dataInicio(),
                projetoRequestBody.dataFim()
        );
    }
}
