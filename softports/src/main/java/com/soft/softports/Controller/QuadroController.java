package com.soft.softports.Controller;

import com.soft.softports.Model.Projeto;
import com.soft.softports.Model.Quadro;
import com.soft.softports.Repository.dto.request.QuadroRequestBody;
import com.soft.softports.Repository.dto.response.Pagina;
import com.soft.softports.Repository.dto.response.QuadroResponse;
import com.soft.softports.Repository.interfaces.ProjetoRepository;
import com.soft.softports.Repository.interfaces.QuadroRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class QuadroController {

    QuadroRepository quadroRepository;
    ProjetoRepository projetoRepository;

    @GetMapping("/quadro")
    public Pagina<QuadroResponse> getAllQuadros() {
        List<Quadro> quadros = quadroRepository.findAll();
        List<QuadroResponse> quadroResponses = quadros.stream()
                .map(this::convertToQuadroResponse)
                .toList();
        return paginar(1000, 0, quadroResponses);
    }

    @GetMapping("/quadro/{id}")
    public ResponseEntity<QuadroResponse> getQuadroById(@PathVariable Long id) {
        Optional<Quadro> quadroOptional = quadroRepository.findById(id);
        return quadroOptional.map(quadro -> ResponseEntity.ok(convertToQuadroResponse(quadro)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/quadro")
    public QuadroResponse saveQuadro(@RequestBody QuadroRequestBody quadroRequestBody) {
        return criarQuadro(quadroRequestBody);
    }

    @DeleteMapping("/quadro/{id}")
    public void deleteQuadro(@PathVariable Long id) {
        quadroRepository.deleteById(id);
    }

    private QuadroResponse convertToQuadroResponse(Quadro quadro) {
        return new QuadroResponse(
                quadro.getQuadroId(),
                quadro.getTitulo(),
                quadro.getProjeto().getProjetoId()
        );
    }

    private Pagina<QuadroResponse> paginar(Integer tamanhoPagina,
                                            Integer numeroPagina,
                                            List<QuadroResponse> infoQuadros) {
        int totalRegistros = infoQuadros.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / tamanhoPagina);

        int fromIndex = numeroPagina * tamanhoPagina;
        int toIndex = Math.min(fromIndex + tamanhoPagina, totalRegistros);

        List<QuadroResponse> conteudo;
        if (fromIndex > totalRegistros) {
            conteudo = List.of();
        } else {
            conteudo = infoQuadros.subList(fromIndex, toIndex);
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

    private QuadroResponse criarQuadro(QuadroRequestBody quadroRequestBody) {
        Projeto projeto = projetoRepository.findById(quadroRequestBody.projetoId()).stream().toList().get(0);
        Quadro quadro = new Quadro(
                quadroRequestBody.titulo(),
                projeto
        );
        quadroRepository.save(quadro);
        return new QuadroResponse(
                null,
                quadroRequestBody.titulo(),
                projeto.getProjetoId()
        );
    }
}
