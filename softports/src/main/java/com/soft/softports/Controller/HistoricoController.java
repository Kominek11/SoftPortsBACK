package com.soft.softports.Controller;

import com.soft.softports.Model.Historico;
import com.soft.softports.Model.Projeto;
import com.soft.softports.Repository.interfaces.HistoricoRepository;
import com.soft.softports.Repository.interfaces.ProjetoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class HistoricoController {

    HistoricoRepository historicoRepository;

    @GetMapping("/historico")
    public List<Historico> getAllHistoricos() {
        return historicoRepository.findAll();
    }

    @GetMapping("/historico/{id}")
    public ResponseEntity<Historico> getHistoricoById(@PathVariable Long id) {
        Optional<Historico> projetoOptional = historicoRepository.findById(id);
        return projetoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/historico")
    public Historico saveHistorico(@RequestBody Historico historico) {
        return historicoRepository.save(historico);
    }

    @DeleteMapping("/historico/{id}")
    public void deleteHistorico(@PathVariable Long id) {
        historicoRepository.deleteById(id);
    }
}
