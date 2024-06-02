package com.soft.softports.Repository.interfaces;

import com.soft.softports.Model.Projeto;
import com.soft.softports.Repository.dto.ProjetoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

}
