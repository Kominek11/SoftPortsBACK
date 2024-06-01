package com.soft.softports.Controller;

import com.soft.softports.Model.Projeto;
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
    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    @GetMapping("/projeto/{id}")
    public ResponseEntity<Projeto> getProjetoById(@PathVariable Long id) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(id);
        return projetoOptional.map(ResponseEntity::ok)
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
}
