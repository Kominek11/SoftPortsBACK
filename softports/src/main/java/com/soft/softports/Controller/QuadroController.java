package com.soft.softports.Controller;

import com.soft.softports.Model.Quadro;
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

    @GetMapping("/quadro")
    public List<Quadro> getAllQuadros() {
        return quadroRepository.findAll();
    }

    @GetMapping("/quadro/{id}")
    public ResponseEntity<Quadro> getQuadroById(@PathVariable Long id) {
        Optional<Quadro> projetoOptional = quadroRepository.findById(id);
        return projetoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/quadro")
    public Quadro saveQuadro(@RequestBody Quadro quadro) {
        return quadroRepository.save(quadro);
    }

    @DeleteMapping("/quadro/{id}")
    public void deleteQuadro(@PathVariable Long id) {
        quadroRepository.deleteById(id);
    }
}
