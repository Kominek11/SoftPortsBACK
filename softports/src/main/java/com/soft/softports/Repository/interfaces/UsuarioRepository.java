package com.soft.softports.Repository.interfaces;

import com.soft.softports.Model.Tarefa;
import com.soft.softports.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
