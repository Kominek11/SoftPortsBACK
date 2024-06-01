package com.soft.softports.Controller;

import com.soft.softports.Model.Tarefa;
import com.soft.softports.Repository.interfaces.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TarefaController {

    TarefaRepository tarefaRepository;

    @GetMapping("/tarefa")
    public List<Tarefa> getAllTarefas() {
        return tarefaRepository.findAll();
    }

    @GetMapping("/tarefa/{id}")
    public ResponseEntity<Tarefa> getTarefaById(@PathVariable Long id) {
        Optional<Tarefa> projetoOptional = tarefaRepository.findById(id);
        return projetoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tarefa")
    public Tarefa saveTarefa(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @DeleteMapping("/tarefa/{id}")
    public void deleteTarefa(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
    }
}
