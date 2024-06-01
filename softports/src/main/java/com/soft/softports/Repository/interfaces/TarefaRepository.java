package com.soft.softports.Repository.interfaces;

import com.soft.softports.Model.Quadro;
import com.soft.softports.Model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}
